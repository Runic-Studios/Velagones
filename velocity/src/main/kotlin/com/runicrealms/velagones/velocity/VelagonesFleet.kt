package com.runicrealms.velagones.velocity

import com.runicrealms.velagones.velocity.api.autoscaler.Autoscaler
import com.runicrealms.velagones.velocity.config.FleetConfig
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger

/**
 * A group of PaperMC servers that are
 * - All recognized by Velagones
 * - Connected to the Velocity Proxy
 * - Discovered through Agones And also:
 * - Are all in the same Agones fleet
 * - Can be autoscaled as a group
 */
class VelagonesFleet(
    proxy: ProxyServer,
    plugin: VelagonesPlugin,
    logger: Logger,
    config: FleetConfig,
    name: String,
    val autoscaler: Autoscaler? = null,
) : VelagonesGroup(proxy, plugin, logger, name, config.serverCapacity) {

    init {
        if (autoscaler != null) {
            AutoscalerEndpoint(proxy, plugin, logger, autoscaler, config.autoscaler, registry)
        }
    }
}
