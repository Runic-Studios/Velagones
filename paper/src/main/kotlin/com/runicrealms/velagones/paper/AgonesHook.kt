package com.runicrealms.velagones.paper

import com.google.inject.Inject
import io.grpc.ManagedChannel
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder
import io.grpc.netty.shaded.io.netty.channel.nio.NioEventLoopGroup
import io.grpc.netty.shaded.io.netty.channel.socket.nio.NioSocketChannel
import java.time.Duration
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import net.infumia.agones4j.Agones
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.server.ServerLoadEvent
import org.bukkit.event.server.ServerLoadEvent.LoadType
import org.slf4j.Logger

class AgonesHook
@Inject
constructor(private val logger: Logger, private val plugin: VelagonesPlugin) : Listener {

    init {
        Bukkit.getPluginManager().registerEvents(this, plugin)
    }

    private val port =
        System.getenv("AGONES_SDK_GRPC_PORT")?.toIntOrNull()
            ?: throw IllegalArgumentException(
                "AGONES_SDK_GRPC_PORT environment variable is undefined, ensure we are running on an Agones GameServer"
            )

    private val gameServerWatcherExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    private val healthCheckExecutor: ScheduledExecutorService =
        Executors.newSingleThreadScheduledExecutor()
    private val channelEventLoopGroup = NioEventLoopGroup(1)
    private val channel: ManagedChannel =
        NettyChannelBuilder.forAddress("localhost", port)
            .usePlaintext()
            .eventLoopGroup(channelEventLoopGroup)
            .channelType(NioSocketChannel::class.java)
            .build()
    val agones =
        Agones.builder()
            .withChannel(channel)
            .withGameServerWatcherExecutor(gameServerWatcherExecutor)
            .withHealthCheck(
                Duration.ofSeconds(1L), // Delay
                Duration.ofSeconds(2L), // Period
            )
            .withHealthCheckExecutor(healthCheckExecutor)
            .build()

    init {
        logger.info("Instantiated Agones SDK hook on localhost:$port")
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
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onServerLoad(event: ServerLoadEvent) {
        if (event.type != LoadType.STARTUP) return
        logger.info("Server finished loading, marking READY in Agones...")
        Bukkit.getScheduler().runTaskAsynchronously(plugin, Runnable { agones.ready() })
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        if (Bukkit.getOnlinePlayers().size > 1) return
        logger.info("First player joined, marking ALLOCATED in Agones...")
        Bukkit.getScheduler().runTaskAsynchronously(plugin, Runnable { agones.allocate() })
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerQuit(event: PlayerQuitEvent) {
        if (Bukkit.getOnlinePlayers().size > 1) return
        logger.info("Last player leaving, marking READY in Agones...")
        Bukkit.getScheduler().runTaskAsynchronously(plugin, Runnable { agones.ready() })
    }

    fun shutdown() {
        gameServerWatcherExecutor.shutdown()
        healthCheckExecutor.shutdown()
        gameServerWatcherExecutor.awaitTermination(5, TimeUnit.SECONDS)
        healthCheckExecutor.awaitTermination(5, TimeUnit.SECONDS)
        channel.shutdown()
        if (!channel.awaitTermination(5, TimeUnit.SECONDS)) {
            channel.shutdownNow()
        }
        // Explicitly await the Netty event loop thread's full exit. Without this,
        // EpollEventLoop.closeAll() can run after Paper closes the plugin JAR, causing
        // "zip file closed" errors because class loading fails on the closed classloader.
        channelEventLoopGroup.shutdownGracefully(0, 0, TimeUnit.SECONDS).await(5, TimeUnit.SECONDS)
    }
}
