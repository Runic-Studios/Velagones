package com.runicrealms.velagones.velocity

import com.google.inject.Inject
import com.runicrealms.velagones.velocity.config.VelagonesConfig
import com.velocitypowered.api.proxy.ProxyServer
import java.util.concurrent.ConcurrentHashMap
import org.slf4j.Logger

/** Represents all the registered server groups on the Velagones plugin. */
class VelagonesFleetRegistry
@Inject
constructor(proxy: ProxyServer, plugin: VelagonesPlugin, logger: Logger, config: VelagonesConfig) {

    val fleets = ConcurrentHashMap<String, VelagonesFleet>()

    init {
        for (fleetConfig in config.fleets) {
            val name = fleetConfig.name
            logger.info("Registering server group $name")
            fleets[name] = VelagonesFleet(proxy, plugin, logger, fleetConfig, name)
        }
    }
}
