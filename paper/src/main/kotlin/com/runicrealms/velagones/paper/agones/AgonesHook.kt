package com.runicrealms.velagones.paper.agones

import com.google.inject.Inject
import com.runicrealms.velagones.paper.VelagonesComponent
import io.grpc.ManagedChannelBuilder
import java.time.Duration
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import net.infumia.agones4j.Agones
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.slf4j.Logger

@VelagonesComponent
class AgonesHook @Inject constructor(private val logger: Logger) : Listener {

    private val allocated = false

    private val gameServerWatcherExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    private val healthCheckExecutor: ScheduledExecutorService =
        Executors.newSingleThreadScheduledExecutor()
    val agones: Agones =
        Agones.builder()
            .withAddress("localhost", 9357)
            .withChannel(ManagedChannelBuilder.forAddress("localhost", 9357).usePlaintext().build())
            .withGameServerWatcherExecutor(gameServerWatcherExecutor)
            .withHealthCheck(
                Duration.ofSeconds(1L), // Delay
                Duration.ofSeconds(2L), // Period
            )
            .withHealthCheckExecutor(healthCheckExecutor)
            .build()

    init {
        logger.info("Instantiated Agones hook on localhost:9357")
        if (agones.canHealthCheck()) {
            agones.startHealthChecking()
            logger.info("Began Agones health checking")
        } else {
            throw IllegalStateException("Failed to begin Agones health checking")
        }
        if (agones.canWatchGameServer()) {
            agones.addGameServerWatcher {
                logger.info("Received state updated from Agones: {}", it.status.state)
            }
        } else {
            logger.warn("Failed to add game server watcher: Not allowed")
        }
        agones.ready()
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        if (!allocated) agones.allocate()
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        if (allocated) agones.shutdown()
    }
}
