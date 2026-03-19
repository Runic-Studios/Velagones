package com.runicrealms.velagones.paper

import co.aikar.commands.PaperCommandManager
import com.google.inject.AbstractModule
import com.google.inject.Inject
import com.google.inject.Provider
import com.google.inject.Scopes
import org.slf4j.Logger

class VelagonesModule(private val plugin: VelagonesPlugin, private val logger: Logger) :
    AbstractModule() {

    override fun configure() {
        bind(VelagonesPlugin::class.java).toInstance(plugin)
        bind(Logger::class.java).toInstance(logger)
        bind(PaperCommandManager::class.java)
            .toProvider(PaperCommandManagerProvider::class.java)
            .`in`(Scopes.SINGLETON)
        bind(AgonesHook::class.java).asEagerSingleton()
        bind(VelagonesService::class.java).asEagerSingleton()
        bind(VelagonesCommand::class.java).asEagerSingleton()
    }

    private class PaperCommandManagerProvider
    @Inject
    constructor(private val plugin: VelagonesPlugin) : Provider<PaperCommandManager> {
        override fun get(): PaperCommandManager = PaperCommandManager(plugin)
    }
}
