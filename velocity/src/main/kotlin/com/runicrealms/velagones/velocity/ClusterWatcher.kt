package com.runicrealms.velagones.velocity

import com.google.inject.Inject
import com.runicrealms.velagones.velocity.config.VelagonesConfig
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.server.ServerInfo
import dev.agones.v1.GameServer
import dev.agones.v1.GameServerStatus
import io.fabric8.kubernetes.client.KubernetesClientBuilder
import io.fabric8.kubernetes.client.Watcher
import io.fabric8.kubernetes.client.WatcherException
import java.net.InetSocketAddress
import org.slf4j.Logger

class ClusterWatcher
@Inject
constructor(
    proxy: ProxyServer,
    private val logger: Logger,
    plugin: VelagonesPlugin,
    private val config: VelagonesConfig,
    private val serverGroupSet: ServerGroupSet,
) {

    init {
        proxy.scheduler
            .buildTask(
                plugin,
                Runnable {
                    try {
                        watch()
                    } catch (exception: Exception) {
                        exception.printStackTrace()
                    }
                },
            )
            .schedule()
    }

    private fun watch() {
        logger.info("Starting K8s watch for Agones GameServer updates")
        val client = KubernetesClientBuilder().build()
        client
            .resources(GameServer::class.java)
            .inNamespace(config.cluster.gameServerNamespace)
            .watch(
                object : Watcher<GameServer> {
                    override fun eventReceived(action: Watcher.Action, resource: GameServer?) {
                        try {
                            updateServer(resource ?: return)
                        } catch (exception: Exception) {
                            logger.error("Failed to handle updating Agones GameServer")
                            exception.printStackTrace()
                        }
                    }

                    override fun onClose(cause: WatcherException?) {
                        logger.info("Agones GameServer watch closed: ${cause?.message}")
                        watch()
                    }
                }
            )
    }

    private fun updateServer(gameServer: GameServer) {
        val name = gameServer.metadata.name
        val status = gameServer.status
        status?.state ?: return

        if (
            status.state != GameServerStatus.State.READY &&
                status.state != GameServerStatus.State.UNHEALTHY &&
                status.state != GameServerStatus.State.SHUTDOWN
        )
            return

        val ports = gameServer.spec.ports
        val gamePort = ports.firstOrNull { it.name == "game" }?.hostPort?.toInt()
        if (gamePort == null) {
            logger.warn(
                "Server $name has no game port named \"game\" in Agones fleet spec, make sure you configured it correctly"
            )
            return
        }
        val grpcPort = config.cluster.paperGrpcPort
        // Note that for gRPC we need the pod IP for internal communication
        val grpcAddress = status.addresses.firstOrNull { it.type == "PodIP" }?.address
        if (grpcAddress == null) {
            val addresses = status.addresses.map { "${it.address}: ${it.type}" }.joinToString(", ")
            logger.warn("Server $name didn't have address with type PodIP, only found: $addresses")
            return
        }

        // This depends on how your node address is configured
        // https://agones.dev/site/docs/reference/gameserver/#primary-address-vs-addresses
        // This should be what velocity uses, since it needs to allow player connections on the node
        // IP
        val nodeAddress = status.address

        // Some of these ("capacity", "group") are used by Velagones to store information
        // The rest are passed on to the VelagonesGameServers
        val labels = gameServer.metadata.labels

        val groupLabel = labels["group"]
        if (groupLabel == null) {
            logger.warn("Server $name is missing group label, ignoring")
            return
        }
        val group = serverGroupSet.groups[groupLabel]
        if (group == null) {
            logger.warn("Server $name has unknown group \"$groupLabel\", ignoring")
            return
        }

        val capacity = labels["capacity"]?.toIntOrNull()
        if (capacity == null) {
            logger.warn("Server $name is missing capacity label, ignoring")
            return
        }

        val target = group.registry.connected[name]
        if (
            status.state == GameServerStatus.State.SHUTDOWN ||
                status.state == GameServerStatus.State.UNHEALTHY
        ) {
            target ?: return
            logger.info("Removing game server $name since Agones marked it as shutdown/unhealthy")
            group.registry.remove(target)
            return
        } else if (status.state == GameServerStatus.State.READY) {
            if (target != null) return

            logger.info(
                "Attempting to discover new Agones GameServer $name on address $nodeAddress:$gamePort with gRPC server $grpcAddress:$grpcPort"
            )
            val info = ServerInfo(name, InetSocketAddress(nodeAddress, gamePort))
            group.registry.discover(info, labels, groupLabel, capacity, grpcAddress, grpcPort)
        }
    }
}
