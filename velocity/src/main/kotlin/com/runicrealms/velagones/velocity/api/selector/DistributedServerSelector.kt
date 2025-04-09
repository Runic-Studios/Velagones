package com.runicrealms.velagones.velocity.api.selector

import com.runicrealms.velagones.velocity.api.VelagonesGameServer
import com.velocitypowered.api.proxy.Player

/** Always selects the emptiest, non-deactivated server for new players to connect to. */
class DistributedServerSelector : ServerSelector {

    override fun selectServer(
        player: Player,
        activeServers: Collection<VelagonesGameServer>,
    ): ServerSelector.Response {
        val server = activeServers.minByOrNull { it.registeredServer.playersConnected.size }
        server ?: return ServerSelector.Response.kick("No servers are registered in Velagones")
        if (server.registeredServer.playersConnected.size >= server.capacity) {
            return ServerSelector.Response.kick("No servers have capacity available")
        }
        return ServerSelector.Response.server(server)
    }
}
