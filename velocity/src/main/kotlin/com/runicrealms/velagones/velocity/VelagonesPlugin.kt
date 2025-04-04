package com.runicrealms.velagones.velocity

import com.google.inject.Guice
import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import java.nio.file.Path
import org.slf4j.Logger

@Plugin(
    id = "velagones",
    name = "Velagones",
    version = "1.0",
    description = "The Agones-Velocity Bridge",
)
class VelagonesPlugin
@Inject
constructor(
    private val proxy: ProxyServer,
    private val logger: Logger,
    @DataDirectory private val dataDirectory: Path,
) {

    @Subscribe
    fun onInitialize(event: ProxyInitializeEvent) {
        // Inject the Guice
        val module = VelagonesModule(this, proxy, logger, dataDirectory)
        val injector = Guice.createInjector(module)

        // Register listeners with velocity
        for (listenerClass in module.listeners) {
            val listener = injector.getInstance(listenerClass)
            proxy.eventManager.register(this, listener)
            logger.info("Registered VelagonesListener in velocity ${listener.javaClass.name}")
        }
    }
}
