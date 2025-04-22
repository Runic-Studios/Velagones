package com.runicrealms.velagones.paper.api.event

import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * Fires immediately after this server has been discovered and registered in the Velocity Proxy by
 * Velagones.
 *
 * @param name The name we have been given by the proxy
 */
data class VelagonesDiscoverEvent(val name: String) : Event(true) {

    companion object {
        private val HANDLERS: HandlerList = HandlerList()

        @JvmStatic fun getHandlerList() = HANDLERS
    }

    override fun getHandlers() = HANDLERS
}
