package com.runicrealms.velagones.velocity.api.event.server

import com.runicrealms.velagones.velocity.api.VelagonesGameServer

/**
 * This event fires after Velagones has messaged a server telling it to shut down. The only current
 * reason for deactivation involves autoscaling down.
 */
data class VelagonesDeactivateServerEvent(val server: VelagonesGameServer)
