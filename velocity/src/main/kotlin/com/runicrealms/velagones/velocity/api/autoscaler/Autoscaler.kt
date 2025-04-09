package com.runicrealms.velagones.velocity.api.autoscaler

/** Autoscaler calculator that Velagones attaches to server groups. */
interface Autoscaler {

    /**
     * Calculates our target server amount based off of the current player count and number of
     * active servers.
     *
     * If the number returned is below the activeServerCount, we will scale down by that different.
     * If the number returned is above the activeServerCount, we will request from Agones to receive
     * that many more game servers.
     *
     * playerCount includes players connected to deactivated servers.
     */
    fun calculate(playerCount: Int, activeServerCount: Int): Int
}
