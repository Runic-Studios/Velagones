package com.runicrealms.velagones.velocity

import com.google.inject.Guice
import com.google.inject.Inject
import com.google.inject.util.Modules
import com.runicrealms.velagones.velocity.api.event.VelagonesInjectEvent
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
    fun onProxyInitialize(event: ProxyInitializeEvent) {
        // Load the module
        val module = VelagonesModule(this, proxy, logger, dataDirectory)

        // Load overrides
        val overrides = proxy.eventManager.fire(VelagonesInjectEvent()).get().overrides
        val stackedModule = Modules.override(module).with(overrides)

        // Inject the Guice
        Guice.createInjector(stackedModule)
    }
}
