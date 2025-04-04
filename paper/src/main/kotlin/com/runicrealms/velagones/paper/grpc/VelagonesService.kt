package com.runicrealms.velagones.paper.grpc

import com.google.inject.Inject
import com.runicrealms.velagones.paper.VelagonesComponent
import com.runicrealms.velagones.paper.VelagonesPlugin
import com.runicrealms.velagones.paper.agones.AgonesHook
import com.runicrealms.velagones.service.*
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import org.slf4j.Logger

@VelagonesComponent
class VelagonesService
@Inject
constructor(
    private val logger: Logger,
    private val agonesHook: AgonesHook,
    private val plugin: VelagonesPlugin,
) : VelagonesPaperGrpcKt.VelagonesPaperCoroutineImplBase(), Listener {

    var discovered = false
    var serverName: String? = null
    var deactivated = false

    override suspend fun discover(request: DiscoverRequest): DiscoverResponse {
        discovered = true
        serverName = request.serverName
        logger.info("Velocity proxy has requested to discover us, naming us $serverName")
        return DiscoverResponse.newBuilder().setSuccess(true).build()
    }

    override suspend fun deactivate(request: DeactivateRequest): DeactivateResponse {
        deactivated = true
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
