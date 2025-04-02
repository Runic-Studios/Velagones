package com.runicrealms.velagones.connect

import com.google.inject.Inject
import com.runicrealms.velagones.VelagonesComponent
import com.runicrealms.velagones.VelagonesConfig
import com.runicrealms.velagones.VelagonesListener
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.player.ServerPreConnectEvent
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger

@VelagonesComponent
@VelagonesListener
class ConnectionHandler
@Inject
constructor(
    private val proxy: ProxyServer,
    private val logger: Logger,
    private val config: VelagonesConfig,
) {

    @Subscribe
    fun onServerPreConnect(event: ServerPreConnectEvent) {
        val emptiest = proxy.allServers.minByOrNull { it.playersConnected.size }
        if (emptiest == null) {
            event.result = ServerPreConnectEvent.ServerResult.denied()
            logger.warn("Denied player ${event.player.uniqueId} entry: no server found")
            return
        }

        if (emptiest.playersConnected.size >= config.maxPlayersPerServer) {
            event.result = ServerPreConnectEvent.ServerResult.denied()
            logger.warn("Denied player ${event.player.uniqueId} entry: all servers full")
        }

        event.result = ServerPreConnectEvent.ServerResult.allowed(emptiest)

        // TODO: implement autoscaling
    }
}
