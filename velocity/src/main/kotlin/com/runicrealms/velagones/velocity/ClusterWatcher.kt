package com.runicrealms.velagones.velocity

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
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
    private val registry: VelagonesRegistry,
    private val config: VelagonesConfig,
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
        val client = KubernetesClientBuilder().build()
        logger.info(
            "Starting K8s watch for Agones GameServer updates in namespace {}",
            client.namespace,
        )
        client
            .resources(GameServer::class.java)
            .inNamespace(client.namespace)
            .watch(
                object : Watcher<GameServer> {
                    override fun eventReceived(action: Watcher.Action, resource: GameServer?) {
                        try {
                            updateServer(resource ?: return)
                        } catch (exception: Exception) {
                            logger.error("Failed to handle updating Agones GameServer", exception)
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

        val gamePort =
            gameServer.status.ports
                .firstOrNull { it.name == "game" }
                ?.port // this should be the hostPort
        val grpcPort =
            gameServer.status.ports
                .firstOrNull { it.name == "grpc" }
                ?.port // this should be the containerPort
        val portsString = jacksonObjectMapper().writeValueAsString(gameServer.status.ports)
        if (gamePort == null) {
            logger.warn(
                "Server $name has no port named \"game\" in Agones fleet spec, found instead {$portsString}, make sure you configured it correctly"
            )
            return
        }
        if (grpcPort == null) {
            logger.warn(
                "Server $name has no port named \"grpc\" in Agones fleet spec, found instead {$portsString}, make sure you configured it correctly"
            )
            return
        }

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

        val labels = gameServer.metadata.labels ?: return

        val fleetLabel = labels["agones.dev/fleet"]
        var group: VelagonesGroup? = null

        if (fleetLabel != null) {
            group = registry.fleets.getOrDefault(fleetLabel, null)
        }
        if (group == null) {
            if (config.trackRogues == true) {
                group = registry.rogues
                logger.info("Identified rogue game server to collect $name")
            } else {
                logger.info("Ignoring unknown/rogue game server $name")
                return
            }
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
            val info = ServerInfo(name, InetSocketAddress(nodeAddress, gamePort.toInt()))
            group.registry.discover(info, grpcAddress, grpcPort.toInt())
        }
    }
}
