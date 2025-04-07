package com.runicrealms.velagones.velocity

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.DisconnectEvent
import com.velocitypowered.api.event.player.ServerPreConnectEvent
import com.velocitypowered.api.event.proxy.ProxyPingEvent
import com.velocitypowered.api.proxy.ProxyServer
import kotlin.math.ceil
import org.slf4j.Logger

@VelagonesComponent
@VelagonesListener
class ConnectionHandler
@Inject
constructor(
    plugin: VelagonesPlugin,
    private val proxy: ProxyServer,
    private val logger: Logger,
    private val config: VelagonesConfig,
    private val serverRegistry: ServerRegistry,
) {
    private var lastScaleUp = 0L
    private var lastScaleDown = 0L

    // How many servers we want right now
    var fleetSize = config.minServers
        private set

    init {
        proxy.eventManager.register(plugin, this)
    }

    @Subscribe
    fun onPing(event: ProxyPingEvent) {
        logger.info("Received ping from ${event.connection.remoteAddress.address.hostAddress}")
    }

    @Subscribe
    fun onServerPreConnect(event: ServerPreConnectEvent) {
        val emptiest = proxy.allServers.minByOrNull { it.playersConnected.size }
        if (emptiest == null) {
            event.result = ServerPreConnectEvent.ServerResult.denied()
            logger.warn("Denied player ${event.player.uniqueId} entry: no server found")
            return
        }

        if (emptiest.playersConnected.size >= config.scalingServerCapacity) {
            event.result = ServerPreConnectEvent.ServerResult.denied()
            logger.warn("Denied player ${event.player.uniqueId} entry: all servers full")
            return
        }

        logger.info(
            "Intercepted login from player ${event.player.uniqueId}, sending to ${emptiest.serverInfo.name}"
        )

        event.result = ServerPreConnectEvent.ServerResult.allowed(emptiest)

        autoscale()
    }

    @Subscribe
    fun onDisconnect(event: DisconnectEvent) {
        autoscale()
    }

    private fun autoscale() {
        val numPlayers = serverRegistry.playerCount()
        val numServers = serverRegistry.amountActive()
        val serverCapacity = config.scalingServerCapacity
        var capacity = numServers * serverCapacity

        val targetCapacity = ceil(config.scalingCapacityFactor * numPlayers).toInt()
        val totalServersNeeded = ceil(targetCapacity.toDouble() / serverCapacity).toInt()

        if (totalServersNeeded > numServers) {
            val timeSinceScaleUp = (System.currentTimeMillis() - lastScaleUp) / 1000
            if (
                timeSinceScaleUp >= config.scalingUpMinSecondsBefore &&
                    capacity < config.scalingCapacityFactor * numPlayers
            ) {
                // SCALE UP
                fleetSize = totalServersNeeded
                lastScaleUp = System.currentTimeMillis()
            }
        } else {
            // Total amount of servers, active or not
            fleetSize = serverRegistry.connected.size
        }

        val timeSinceScaleDown = (System.currentTimeMillis() - lastScaleDown) / 1000
        if (
            timeSinceScaleDown >= config.scalingDownMinSecondsBefore &&
                capacity >
                    config.scalingCapacityFactor * numPlayers * config.scalingDownHysteresis &&
                numServers >= config.minServers
        ) {
            // SCALE DOWN
            val serversSorted =
                serverRegistry.connected.values
                    .filter { !it.deactivated }
                    .sortedBy { proxy.getServer(it.info.name).get().playersConnected.size }
            for (server in serversSorted) {
                if (capacity <= config.minServers * serverCapacity) return
                if (capacity - serverCapacity >= ceil(config.scalingCapacityFactor * numPlayers)) {
                    logger.info("Deactivating server ${server.info.name} due to scale down")
                    serverRegistry.deactivate(server.info.name, false)
                    capacity -= serverCapacity
                } else {
                    break
                }
            }
            lastScaleDown = System.currentTimeMillis()
        }
    }
}
