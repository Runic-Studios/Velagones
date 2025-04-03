package com.runicrealms.velagones.velocity.agones

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.google.inject.Inject
import com.runicrealms.velagones.velocity.VelagonesComponent
import com.runicrealms.velagones.velocity.VelagonesConfig
import com.runicrealms.velagones.velocity.VelagonesPlugin
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.server.ServerInfo
import dev.agones.v1.GameServer
import dev.agones.v1.GameServerStatus
import io.kubernetes.client.openapi.Configuration
import io.kubernetes.client.openapi.JSON
import io.kubernetes.client.openapi.apis.CustomObjectsApi
import io.kubernetes.client.util.Config
import io.kubernetes.client.util.Watch
import java.lang.reflect.Type
import java.net.InetSocketAddress
import java.time.ZonedDateTime
import kotlin.jvm.optionals.getOrNull
import org.slf4j.Logger

@VelagonesComponent
class AgonesHook
@Inject
constructor(
    private val proxy: ProxyServer,
    private val logger: Logger,
    plugin: VelagonesPlugin,
    private val config: VelagonesConfig,
) {

    init {
        proxy.scheduler
            .buildTask(
                plugin,
                Runnable {
                    while (true) {
                        try {
                            logger.info("Starting K8s watch for gameserver updates")
                            watch()
                        } catch (exception: Exception) {
                            exception.printStackTrace()
                        }
                    }
                },
            )
            .schedule()
    }

    private fun watch() {
        logger.info("Watching over game-server-namespace ${config.gameServerNamespace}")

        val client = Config.fromCluster()
        // Necessary because of java 9+ private changes
        JSON.setGson(
            GsonBuilder()
                .registerTypeAdapter(
                    ZonedDateTime::class.java,
                    object : JsonDeserializer<ZonedDateTime> {
                        override fun deserialize(
                            json: JsonElement,
                            typeOfT: Type,
                            context: JsonDeserializationContext,
                        ): ZonedDateTime {
                            return ZonedDateTime.parse(json.asString)
                        }
                    },
                )
                .create()
        )

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
                    .buildCall(null),
                object : TypeToken<Watch.Response<GameServer>>() {}.type,
            )

        for (server in watch) {
            if (server?.`object` == null) {
                logger.info("Skipping null server received $server")
                continue
            }
            try {
                updateServer(server.`object`)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    private fun updateServer(gameServer: GameServer) {
        val name = gameServer.metadata.name
        val status = gameServer.status
        if (status?.state == null) {
            logger.info("Skipping game server with null status $gameServer")
            return
        }

        logger.info("Discovered game server $name with status ${status.state.name}")

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
            val targetServer = proxy.getServer(name).getOrNull() ?: return
            logger.info("Removing game server $name since it is shutting down")
            proxy.unregisterServer(targetServer.serverInfo)
            return
        }

        val port = gamePort.hostPort.toInt()
        val socketAddress = InetSocketAddress(status.address, port)
        val newServer = ServerInfo(name, socketAddress)

        logger.info("Registering new game server $name on address ${status.address}:$port")
        proxy.registerServer(newServer)
    }
}
