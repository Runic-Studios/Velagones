package com.runicrealms.velagones.paper

import com.google.inject.Guice
import com.google.inject.Injector
import org.bukkit.plugin.java.JavaPlugin
import org.slf4j.LoggerFactory

class VelagonesPlugin : JavaPlugin() {

    private val logger = LoggerFactory.getLogger("velagones")

    private lateinit var injector: Injector

    override fun onEnable() {
        val module = VelagonesModule(this, logger)

        // Inject the Guice
        injector = Guice.createInjector(module)
    }

    override fun onDisable() {
        val agonesHook = injector.getInstance(AgonesHook::class.java)
        if (agonesHook != null) {
            logger.info("Marking server as SHUTDOWN in Agones")
            agonesHook.agones.shutdown()
        }
        val service = injector.getInstance(VelagonesService::class.java)
        if (service != null) {
            logger.info("Shutting down Velagones Paper gRPC server")
            service.grpcServer.shutdown()
        }
    }
}
