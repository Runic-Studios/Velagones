package com.runicrealms.velagones.velocity.api.selector

import com.runicrealms.velagones.velocity.api.VelagonesGameServer
import com.runicrealms.velagones.velocity.config.VelagonesConfig
import com.velocitypowered.api.proxy.Player

/**
 * Attempts to fill up each server until target capacity percentage, before moving on to another
 * server.
 *
 * For example, if capacityTarget is set to 0.8 with capacity set to 20, Velagones will attempt to
 * fill up each server with 16 players before moving on to a new one.
 */
class PackedServerSelector(config: VelagonesConfig) : ServerSelector {

    private val capacityTarget =
        config.selector.packed?.targetCapacity
            ?: throw IllegalArgumentException("Missing config: selector.packed.targetCapacity")


    override fun selectServer(
        player: Player,
        activeServers: Collection<VelagonesGameServer>,
    ): ServerSelector.Response {
        var server =
            activeServers
                .filter { it.registeredServer.playersConnected.size < it.capacity * capacityTarget }
                .maxByOrNull { it.registeredServer.playersConnected.size }
        if (server == null) {
            server = activeServers.minByOrNull { it.registeredServer.playersConnected.size }
            server ?: return ServerSelector.Response.kick("No servers are registered in Velagones")
            if (server.registeredServer.playersConnected.size < server.capacity) {
                return ServerSelector.Response.kick("No servers have capacity available")
            }
        }
        return ServerSelector.Response.server(server)
    }
}
