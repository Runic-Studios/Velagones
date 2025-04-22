package com.runicrealms.velagones.velocity.api.event.server

import com.runicrealms.velagones.velocity.api.VelagonesGameServer

/**
 * This event fires after Velagones has:
 * - Found a potential new server in the cluster
 * - Sent it a Discovery Request over gRPC
 * - Received a successful response
 * - Added the server to the Velocity proxy
 */
data class VelagonesDiscoverServerEvent(val server: VelagonesGameServer)
