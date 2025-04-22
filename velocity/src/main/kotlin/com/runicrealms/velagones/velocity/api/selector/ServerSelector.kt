package com.runicrealms.velagones.velocity.api.selector

import com.runicrealms.velagones.velocity.api.VelagonesGameServer
import com.velocitypowered.api.proxy.Player

/** Selects which server new players should connect to. */
interface ServerSelector {

    /**
     * Using a list of the currently active Velagones-aware servers, selects which one the player
     * should connect to.
     *
     * Is only executed on the underlying Velocity PlayerChooseInitialServerEvent. Does not execute
     * when a player transfers between servers, only on initial connection.
     *
     * Deactivated servers are not considered, but activeServers are not guaranteed to have
     * capacity.
     */
    fun selectServer(player: Player, activeServers: Collection<VelagonesGameServer>): Response

    class Response private constructor(val server: VelagonesGameServer?, val kickMessage: String) {

        companion object {
            fun server(server: VelagonesGameServer) = Response(server, "")

            fun kick(kickMessage: String) = Response(null, kickMessage)

            fun kick() = Response(null, "Could not find server to connect to")
        }
    }
}
