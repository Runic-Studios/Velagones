package com.runicrealms.velagones.velocity

import com.google.inject.Inject
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.server.ServerInfo
import dev.agones.v1.GameServer
import dev.agones.v1.GameServerStatus
import io.fabric8.kubernetes.client.KubernetesClientBuilder
import io.fabric8.kubernetes.client.Watcher
import io.fabric8.kubernetes.client.WatcherException
import java.net.InetSocketAddress
import org.slf4j.Logger

@VelagonesComponent
class ClusterWatcher
@Inject
constructor(
    proxy: ProxyServer,
    private val logger: Logger,
    plugin: VelagonesPlugin,
    private val config: VelagonesConfig,
    private val serverRegistry: ServerRegistry,
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
            .inNamespace(config.agonesGameServerNamespace)
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
        ) return

        val ports = gameServer.spec.ports
        val gamePort = ports.firstOrNull { it.name == "game" }?.hostPort?.toInt() ?: return
        val grpcPort = ports.firstOrNull { it.name == "grpc" }?.hostPort?.toInt() ?: return
        val address = status.address


        val target = serverRegistry.connected[name]
        if (
            status.state == GameServerStatus.State.SHUTDOWN ||
                status.state == GameServerStatus.State.UNHEALTHY
        ) {
            target ?: return
            logger.info("Removing game server $name since Agones marked it as shutdown/unhealthy")
            serverRegistry.remove(name)
            return
        } else if (status.state == GameServerStatus.State.READY) {
            if (target != null) return
            logger.info(
                "Attempting to discover new Agones GameServer $name on address $address:$gamePort with gRPC server $grpcPort"
            )
            val info = ServerInfo(name, InetSocketAddress(address, gamePort))
            serverRegistry.discover(info, grpcPort)
        }
    }
}
