package com.runicrealms.velagones.paper

import com.google.inject.AbstractModule
import org.slf4j.Logger

class VelagonesModule(private val plugin: VelagonesPlugin, private val logger: Logger) :
    AbstractModule() {

    override fun configure() {
        bind(VelagonesPlugin::class.java).toInstance(plugin)
        bind(Logger::class.java).toInstance(logger)
        bind(AgonesHook::class.java).asEagerSingleton()
        bind(VelagonesService::class.java).asEagerSingleton()
    }
}
