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
        val HANDLERS: HandlerList = HandlerList()
    }

    override fun getHandlers() = HANDLERS
}
