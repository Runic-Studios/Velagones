package com.runicrealms.velagones.paper

import com.google.inject.Guice
import com.google.inject.Injector
import com.runicrealms.velagones.paper.agones.AgonesHook
import com.runicrealms.velagones.paper.grpc.VelagonesService
import io.grpc.Server
import io.grpc.ServerBuilder
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.slf4j.LoggerFactory

class VelagonesPlugin : JavaPlugin() {

    private val logger = LoggerFactory.getLogger("velagones")

    private lateinit var injector: Injector
    private var grpcServer: Server? = null

    override fun onEnable() {
        val module = VelagonesModule(this, logger)

        // Inject the Guice
        injector = Guice.createInjector(module)

        // Register all listeners
        module.listeners.forEach {
            val listener = injector.getInstance(it) as Listener
            Bukkit.getPluginManager().registerEvents(listener, this)
        }

        val config = injector.getInstance(VelagonesConfig::class.java)
        val service = injector.getInstance(VelagonesService::class.java)
        val agonesHook = injector.getInstance(AgonesHook::class.java)
        // Start gRPC service
        try {
            grpcServer =
                ServerBuilder.forPort(config.servicePort).addService(service).build().start()
            logger.info("Velagones Paper gRPC server started on ${config.servicePort}")
        } catch (exception: Exception) {
            logger.error(
                "Failed to start Velagones Paper gRPC server on port ${config.servicePort}"
            )
            exception.printStackTrace()
            logger.info("Shutting down since we failed to start Velagones Paper gRPC server")
            agonesHook.agones.shutdown()
            Bukkit.shutdown()
        }
    }

    override fun onDisable() {
        injector.getInstance(AgonesHook::class.java).agones.shutdown()
        if (grpcServer != null) {
            logger.info("Shutting down Velagones Paper gRPC server")
            grpcServer!!.shutdown()
        }
    }
}
