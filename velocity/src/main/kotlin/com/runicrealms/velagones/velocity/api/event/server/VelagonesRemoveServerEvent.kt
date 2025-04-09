package com.runicrealms.velagones.velocity.api.event.server

import com.runicrealms.velagones.velocity.api.VelagonesGameServer

/**
 * This event fires just before Velagones removes a server from the proxy. (But after it has been
 * removed from the internal registry). Reasons for removing a server include:
 * - The server was marked as deactivated, and after all its players left, it is shutting down
 * - The server was marked as unhealthy and Agones is terminating it
 * - The Velocity proxy is shutting down and Velagones is removing all of its servers
 */
data class VelagonesRemoveServerEvent(val server: VelagonesGameServer)
