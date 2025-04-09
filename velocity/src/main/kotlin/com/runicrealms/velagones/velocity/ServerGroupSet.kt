package com.runicrealms.velagones.velocity

import com.google.inject.Inject
import com.runicrealms.velagones.velocity.config.VelagonesConfig
import com.velocitypowered.api.proxy.ProxyServer
import java.util.concurrent.ConcurrentHashMap
import org.slf4j.Logger

/** Represents all the registered server groups on the Velagones plugin. */
class ServerGroupSet
@Inject
constructor(proxy: ProxyServer, plugin: VelagonesPlugin, logger: Logger, config: VelagonesConfig) {

    val groups = ConcurrentHashMap<String, ServerGroup>()

    init {
        val addDefault = config.serverGroups.size == 1
        for (groupConfig in config.serverGroups) {
            val name = groupConfig.name
            if (!addDefault && name == "default") continue
            logger.info("Registering server group $name")
            groups[name] = ServerGroup(proxy, plugin, logger, groupConfig, name)
        }
    }
}
