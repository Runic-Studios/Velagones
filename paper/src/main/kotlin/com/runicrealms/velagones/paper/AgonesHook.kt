package com.runicrealms.velagones.paper

import com.google.inject.Inject
import com.runicrealms.velagones.paper.config.VelagonesConfig
import io.grpc.ManagedChannelBuilder
import java.time.Duration
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import net.infumia.agones4j.Agones
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.slf4j.Logger

class AgonesHook
@Inject
constructor(plugin: VelagonesPlugin, private val logger: Logger, config: VelagonesConfig) :
    Listener {

    private val address = config.cluster.agonesSdk.address
    private val port = config.cluster.agonesSdk.port

    init {
        Bukkit.getPluginManager().registerEvents(this, plugin)
    }

    private val allocated = false

    private val gameServerWatcherExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    private val healthCheckExecutor: ScheduledExecutorService =
        Executors.newSingleThreadScheduledExecutor()
    val agones: Agones =
        Agones.builder()
            .withAddress(address, port)
            .withChannel(ManagedChannelBuilder.forAddress(address, port).usePlaintext().build())
            .withGameServerWatcherExecutor(gameServerWatcherExecutor)
            .withHealthCheck(
                Duration.ofSeconds(1L), // Delay
                Duration.ofSeconds(2L), // Period
            )
            .withHealthCheckExecutor(healthCheckExecutor)
            .build()

    init {
        logger.info("Instantiated Agones SDK hook on $address:$port")
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
