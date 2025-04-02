package com.runicrealms.velagones

import com.google.gson.reflect.TypeToken
import com.google.gson.GsonBuilder
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.server.ServerInfo
import dev.agones.v1.GameServer
import dev.agones.v1.GameServerStatus
import io.k8swatcher.annotation.EventType
import io.k8swatcher.annotation.Informer
import io.kubernetes.client.openapi.Configuration
import io.kubernetes.client.openapi.JSON
import io.kubernetes.client.openapi.apis.CustomObjectsApi
import io.kubernetes.client.util.Config
import io.kubernetes.client.util.Watch
import java.net.InetSocketAddress
import kotlin.jvm.optionals.getOrNull
import org.slf4j.Logger
import org.springframework.stereotype.Service

@Service
@Informer(name = "velagones")
class VelagonesService(
    private val config: VelagonesConfig,
    private val proxyServer: ProxyServer,
    private val logger: Logger,
    plugin: VelagonesPlugin,
) {

    init {
        proxyServer.scheduler
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

    fun watch() {
        if (config.gameServerNamespace == null) {
            logger.warn(
                "No game-server-namespace set for velagones to track, skipping monitoring..."
            )
        } else {
            logger.info("Watching over game-server-namespace ${config.gameServerNamespace}")
            val client = Config.fromCluster()
            JSON.setGson(JSON.createGson().excludeFieldsWithoutExposeAnnotation().create())
            Configuration.setDefaultApiClient(client)
            val api = CustomObjectsApi(client)
            val watch =
                Watch.createWatch<GameServer>(
                    client,
                    api.listNamespacedCustomObject(
                            "agones.dev",
                            "v1",
                            config.gameServerNamespace,
                            "gameservers",
                        )
                        .allowWatchBookmarks(true)
                        .watch(true)
                        .limit(1000)
                        .buildCall(null),
                    object : TypeToken<Watch.Response<GameServer>>() {}.type
                )
            for (server in watch) {
                server?.`object` ?: continue
                updateServer(server.`object`)
            }
        }
    }

    @io.k8swatcher.annotation.Watch(event = EventType.ADD, resource = GameServer::class)
    fun onServerAdded(gameServer: GameServer) {
        updateServer(gameServer)
    }

    @io.k8swatcher.annotation.Watch(event = EventType.UPDATE, resource = GameServer::class)
    fun onServerUpdated(oldGameServer: GameServer, gameServer: GameServer) {
        updateServer(gameServer)
    }

    @io.k8swatcher.annotation.Watch(event = EventType.DELETE, resource = GameServer::class)
    fun onServerDeleted(gameServer: GameServer) {
        updateServer(gameServer)
    }

    fun updateServer(gameServer: GameServer) {
        val name = gameServer.metadata.name
        val status = gameServer.status
        logger.info("Discovered game server $name with status $status")

        if (
            status.state != GameServerStatus.State.READY &&
                status.state != GameServerStatus.State.SHUTDOWN
        )
            return

        val ports = gameServer.spec.ports
        val gamePort = ports.firstOrNull { it.name == "game" }

        if (gamePort == null) {
            logger.info("Game server $name has no valid game port, skipping")
            return
        }

        if (status.state == GameServerStatus.State.SHUTDOWN) {
            val targetServer = VelagonesPlugin.proxyServer.getServer(name).getOrNull() ?: return
            logger.info("Removing game server $name since it is shutting down")
            VelagonesPlugin.proxyServer.unregisterServer(targetServer.serverInfo)
            return
        }

        val port = gamePort.hostPort.toInt()
        val socketAddress = InetSocketAddress(status.address, port)
        val newServer = ServerInfo(name, socketAddress)

        logger.info("Registering new game server $name on address ${status.address}:$port")
        proxyServer.registerServer(newServer)
    }
}
