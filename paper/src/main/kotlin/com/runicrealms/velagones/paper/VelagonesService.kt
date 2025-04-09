package com.runicrealms.velagones.paper

import com.google.inject.Inject
import com.runicrealms.velagones.paper.api.event.VelagonesDeactivateEvent
import com.runicrealms.velagones.paper.api.event.VelagonesDiscoverEvent
import com.runicrealms.velagones.paper.config.VelagonesConfig
import com.runicrealms.velagones.service.*
import io.grpc.ServerBuilder
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import org.slf4j.Logger

class VelagonesService
@Inject
constructor(
    private val logger: Logger,
    private val agonesHook: AgonesHook,
    private val plugin: VelagonesPlugin,
    config: VelagonesConfig,
) : VelagonesPaperGrpcKt.VelagonesPaperCoroutineImplBase(), Listener {

    var discovered = false
        private set

    var serverName: String? = null
        private set

    var deactivated = false
        private set

    val port = config.cluster.grpcHostPort

    val grpcServer = let {
        try {
            val grpcServer = ServerBuilder.forPort(port).addService(this).build().start()
            logger.info("Velagones Paper gRPC server started on $port")
            return@let grpcServer
        } catch (exception: Exception) {
            logger.error("Failed to start Velagones Paper gRPC server on port $port")
            logger.info("Shutting down since we failed to start Velagones Paper gRPC server")
            agonesHook.agones.shutdown()
            Bukkit.shutdown()
            throw exception
        }
    }

    init {
        Bukkit.getPluginManager().registerEvents(this, plugin)
    }

    override suspend fun discover(request: DiscoverRequest): DiscoverResponse {
        discovered = true
        serverName = request.serverName
        logger.info("Velocity proxy has requested to discover us, naming us $serverName")
        Bukkit.getPluginManager().callEvent(VelagonesDiscoverEvent(request.serverName))
        return DiscoverResponse.newBuilder().setSuccess(true).build()
    }

    override suspend fun deactivate(request: DeactivateRequest): DeactivateResponse {
        deactivated = true
        Bukkit.getPluginManager().callEvent(VelagonesDeactivateEvent(request.immediate))
        if (request.immediate) {
            logger.info("Velocity proxy has request we deactivate IMMEDIATELY")
            logger.info("Kicking all players and marking us as shutdown in Agones")
            Bukkit.getOnlinePlayers().forEach {
                it.kick(Component.text("Proxy has requested immediate deactivation of server"))
            }
            agonesHook.agones.shutdown()
        } else {
            logger.info("Velocity proxy has requested that we deactivate once all players leave")
        }
        return DeactivateResponse.newBuilder().build()
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        Bukkit.getScheduler()
            .runTaskLater(
                plugin,
                Runnable {
                    if (Bukkit.getOnlinePlayers().isEmpty()) {
                        logger.info(
                            "Last player has left, marking us as shutdown in Agones since we are deactivated"
                        )
                        agonesHook.agones.shutdown()
                        Bukkit.shutdown()
                    }
                },
                1L,
            )
    }
}
