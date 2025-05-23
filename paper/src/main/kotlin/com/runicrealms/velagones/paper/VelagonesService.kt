package com.runicrealms.velagones.paper

import com.google.inject.Inject
import com.runicrealms.velagones.paper.api.event.VelagonesDeactivateEvent
import com.runicrealms.velagones.paper.api.event.VelagonesDiscoverEvent
import com.runicrealms.velagones.service.DeactivateRequest
import com.runicrealms.velagones.service.DeactivateResponse
import com.runicrealms.velagones.service.DiscoverRequest
import com.runicrealms.velagones.service.DiscoverResponse
import com.runicrealms.velagones.service.VelagonesPaperGrpcKt
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
) : VelagonesPaperGrpcKt.VelagonesPaperCoroutineImplBase(), Listener {

    var discovered = false
        private set

    var serverName: String? = null
        private set

    var deactivated = false
        private set

    val port =
        System.getenv("VELAGONES_GRPC_PORT")?.toIntOrNull()
            ?: throw IllegalArgumentException(
                "VELAGONES_GRPC_PORT environment variable is undefined, ensure you have added it in the fleet spec"
            )

    val grpcServer = let {
        try {
            val grpcServer = ServerBuilder.forPort(port).addService(this).build().start()
            logger.info("Velagones Paper gRPC server started on $port")
            return@let grpcServer
        } catch (exception: Exception) {
            logger.error("Failed to start Velagones Paper gRPC server on port $port")
            logger.info("Shutting down since we failed to start Velagones Paper gRPC server")
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
        try {
            Bukkit.getPluginManager().callEvent(VelagonesDiscoverEvent(request.serverName))
        } catch (exception: Exception) {
            logger.error("Failed to broadcast VelagonesDiscoverEvent", exception)
        }
        return DiscoverResponse.newBuilder().setSuccess(true).build()
    }

    override suspend fun deactivate(request: DeactivateRequest): DeactivateResponse {
        deactivated = true
        try {
            Bukkit.getPluginManager().callEvent(VelagonesDeactivateEvent(request.immediate))
        } catch (exception: Exception) {
            logger.error("Failed to broadcast VelagonesDeactivateEvent", exception)
        }
        if (request.immediate) {
            logger.info("Velocity proxy has request we deactivate IMMEDIATELY")
            logger.info("Kicking all players and marking us as shutdown in Agones")
            Bukkit.getScheduler().runTask(plugin, Runnable {
                Bukkit.getOnlinePlayers().forEach {
                    it.kick(Component.text("Proxy has requested immediate deactivation of server"))
                }
                Bukkit.shutdown()
            })
        } else {
            logger.info("Velocity proxy has requested that we deactivate once all players leave")
            if (Bukkit.getOnlinePlayers().isEmpty()) {
                logger.info("Shutting down since there are no players online")
                Bukkit.getScheduler().runTask(plugin, Runnable {
                    Bukkit.shutdown()
                })
            }
        }
        return DeactivateResponse.newBuilder().build()
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        if (deactivated) {
            Bukkit.getScheduler()
                .runTaskLater(
                    plugin,
                    Runnable {
                        if (Bukkit.getOnlinePlayers().isEmpty()) {
                            logger.info(
                                "Last player has left, since we are deactivated, shutting down server"
                            )
                            Bukkit.shutdown()
                        }
                    },
                    1L,
                )
        }
    }
}
