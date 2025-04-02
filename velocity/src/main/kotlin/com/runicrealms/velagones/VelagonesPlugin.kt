package com.runicrealms.velagones

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import java.nio.file.Path
import org.reflections.Reflections
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
) : AbstractModule() {

    private val listeners = HashSet<Class<*>>()

    override fun configure() {
        bind(VelagonesPlugin::class.java).toInstance(this)
        bind(ProxyServer::class.java).toInstance(proxy)
        bind(Logger::class.java).toInstance(logger)
        bind(Path::class.java).annotatedWith(DataDirectory::class.java).toInstance(dataDirectory)

        val reflections = Reflections(this::class.java.`package`.name)

        val componentClasses = reflections.getTypesAnnotatedWith(VelagonesComponent::class.java)
        componentClasses.forEach { bind(it).asEagerSingleton() }

        val listenerClasses = reflections.getTypesAnnotatedWith(VelagonesListener::class.java)
        listenerClasses.forEach {
            if (it in componentClasses) {
                listeners.add(it)
            } else {
                logger.warn(
                    "VelagonesListener ${it.name} is not registered as VelagonesComponent, skipping..."
                )
            }
        }
    }

    @Subscribe
    fun onInitialize(event: ProxyInitializeEvent) {
        val injector = Guice.createInjector(this)

        for (listenerClass in listeners) {
            val listener = injector.getInstance(listenerClass)
            proxy.eventManager.register(this, listener)
            logger.info("Registered VelagonesListener in velocity ${listener.javaClass.name}")
        }
    }
}
