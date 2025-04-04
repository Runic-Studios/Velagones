package com.runicrealms.velagones.paper

import com.google.inject.AbstractModule
import org.bukkit.event.Listener
import org.reflections.Reflections
import org.slf4j.Logger

class VelagonesModule(private val plugin: VelagonesPlugin, private val logger: Logger) :
    AbstractModule() {

    val listeners = HashSet<Class<*>>()

    override fun configure() {
        bind(VelagonesPlugin::class.java).toInstance(plugin)
        bind(Logger::class.java).toInstance(logger)

        val reflections = Reflections(this::class.java.`package`.name)

        val componentClasses = reflections.getTypesAnnotatedWith(VelagonesComponent::class.java)
        componentClasses.forEach {
            bind(it).asEagerSingleton()
            if (it.isInstance(Listener::class.java)) {
                listeners.add(it)
            }
        }
    }
}
