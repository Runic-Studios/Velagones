package com.runicrealms.velagones.velocity.api

interface VelagonesAPI {

    /**
     * Returns a list of all known Velagones-aware game servers. May include rogues, which will have
     * a special group name "velagones-rogues"
     */
    fun getGameServers(): Collection<VelagonesGameServer>
}
