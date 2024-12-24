package com.runicrealms.velagones.paper

import com.djaytan.bukkit.slf4j.BukkitLoggerFactory
import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Injector
import com.runicrealms.velagones.common.loadConfig
import com.runicrealms.velagones.paper.generated.model.VelagonesConfig
import java.io.File
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class VelagonesPaperPlugin : JavaPlugin() {

    private lateinit var injector: Injector
    private lateinit var clusterHook: ClusterHook

    override fun onEnable() {
        // Inject slf4j logger
        BukkitLoggerFactory.provideBukkitLogger(logger)
        val logger = LoggerFactory.getLogger("Velagones")

        // Read our config
        val config =
            loadConfig<VelagonesConfig>(
                File(dataFolder, "config.yml"),
                javaClass.getResourceAsStream("/config.yml"),
                logger,
            )
                ?: run {
                    logger.error("Failed to load config, disabling plugin...")
                    Bukkit.getPluginManager().disablePlugin(this)
                    return
                }

        // Inject the guice
        injector =
            Guice.createInjector(
                object : AbstractModule() {
                    override fun configure() {
                        bind(VelagonesPaperPlugin::class.java).toInstance(this@VelagonesPaperPlugin)
                        bind(Logger::class.java).toInstance(logger)
                        bind(VelagonesConfig::class.java).toInstance(config)
                    }
                }
            )
        logger.info("Guice injection has finished!")

        // Load the Agones hook
        clusterHook = injector.getInstance(ClusterHook::class.java)
        logger.info("Loaded Agones hook!")

        // Join discovery
        clusterHook.discoveryStub.join(clusterHook.gameServer, null)
        logger.info("Joined Velocity Proxy using Velagones Discovery Service!")
    }

    override fun onDisable() {
        clusterHook.discoveryStub.leave(clusterHook.gameServer, null)
        logger.info("Left Velocity Proxy using Velagones Discovery Service!")
    }
}
