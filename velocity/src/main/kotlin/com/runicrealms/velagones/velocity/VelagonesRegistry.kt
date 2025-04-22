package com.runicrealms.velagones.velocity

import com.google.inject.Inject
import com.runicrealms.velagones.velocity.api.autoscaler.DefaultAutoscaler
import com.runicrealms.velagones.velocity.api.event.VelagonesInitializeEvent
import com.runicrealms.velagones.velocity.api.selector.DistributedServerSelector
import com.runicrealms.velagones.velocity.api.selector.PackedServerSelector
import com.runicrealms.velagones.velocity.api.selector.ServerSelector
import com.runicrealms.velagones.velocity.config.AutoscalerConfig
import com.runicrealms.velagones.velocity.config.SelectorConfig
import com.runicrealms.velagones.velocity.config.VelagonesConfig
import com.velocitypowered.api.proxy.ProxyServer
import java.util.concurrent.ConcurrentHashMap
import org.slf4j.Logger

class VelagonesRegistry
@Inject
constructor(
    proxy: ProxyServer,
    plugin: VelagonesPlugin,
    logger: Logger,
    config: VelagonesConfig,
    initializeEvent: VelagonesInitializeEvent,
) {

    /**
     * Represents all the registered fleets on the Velagones plugin. Maps between their name and the
     * VelagonesFleet instance.
     */
    val fleets = ConcurrentHashMap<String, VelagonesFleet>()

    var serverSelector: ServerSelector =
        when (config.selector.type!!) {
            SelectorConfig.Type.DISTRIBUTED -> DistributedServerSelector()
            SelectorConfig.Type.PACKED ->
                PackedServerSelector(
                    config.selector.packed?.targetCapacity
                        ?: throw IllegalArgumentException(
                            "Missing config: selector.packed.targetCapacity"
                        )
                )
            SelectorConfig.Type.CUSTOM ->
                initializeEvent.customServerSelector
                    ?: throw IllegalArgumentException(
                        "You selected selector.type: custom, but never registered a custom selector. Make sure to do this during VelagonesInitializeEvent."
                    )
        }
        internal set

    init {
        for (fleetConfig in config.fleets) {
            val name = fleetConfig.name
            logger.info("Registering server group $name")

            val autoscaler =
                when (fleetConfig.autoscaler.type!!) {
                    AutoscalerConfig.Type.DEFAULT ->
                        DefaultAutoscaler(
                            fleetConfig.autoscaler.default,
                            fleetConfig.serverCapacity,
                            fleetConfig.autoscaler.minServers
                                ?: throw IllegalArgumentException(
                                    "Missing config: autoscaler.minServers"
                                ),
                        )
                    AutoscalerConfig.Type.DISABLED -> null
                    AutoscalerConfig.Type.CUSTOM ->
                        run {
                            val customType =
                                fleetConfig.autoscaler.custom
                                    ?: throw IllegalArgumentException(
                                        "Missing autoscaler.custom type for fleet $name"
                                    )
                            initializeEvent.customAutoscalers[customType]
                                ?: throw IllegalArgumentException(
                                    "Could not find autoscaler.custom type $customType for fleet $name. Make sure you registered this autoscaler during VelagonesInjectEvent."
                                )
                        }
                }

            fleets[name] = VelagonesFleet(proxy, plugin, logger, fleetConfig, name, autoscaler)
        }
    }
}
