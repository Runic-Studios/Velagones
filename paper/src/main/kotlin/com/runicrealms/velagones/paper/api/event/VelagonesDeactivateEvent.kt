package com.runicrealms.velagones.paper.api.event

import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * Fires immediately after this server receives a deactivation notice from Velagones.
 *
 * @param immediate Indicates if we are shutting down immediately
 */
data class VelagonesDeactivateEvent(val immediate: Boolean) : Event() {

    companion object {
        private val HANDLERS: HandlerList = HandlerList()

        @JvmStatic fun getHandlerList() = HANDLERS
    }

    override fun getHandlers() = HANDLERS
}
