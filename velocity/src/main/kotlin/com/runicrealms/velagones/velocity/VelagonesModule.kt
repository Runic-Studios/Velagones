package com.runicrealms.velagones.velocity

import com.google.inject.AbstractModule
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import java.nio.file.Path
import org.reflections.Reflections
import org.slf4j.Logger

class VelagonesModule(
    private val plugin: VelagonesPlugin,
    private val proxy: ProxyServer,
    private val logger: Logger,
    private val dataDirectory: Path,
) : AbstractModule() {

    val listeners = HashSet<Class<*>>()

    override fun configure() {
        bind(VelagonesPlugin::class.java).toInstance(plugin)
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
}
