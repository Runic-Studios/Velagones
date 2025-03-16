package com.runicrealms.velagones

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import java.io.File
import java.nio.file.Path
import org.slf4j.Logger
import org.springframework.boot.SpringApplication
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.PropertiesPropertySource
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.support.PropertiesLoaderUtils

@Plugin(
    id = "velagones",
    name = "Velagones",
    version = "1.0",
    description = "The Agones-Velocity Bridge",
)
class VelagonesPlugin
@Inject
constructor(
    proxyServer: ProxyServer,
    logger: Logger,
    @DataDirectory private val dataDirectory: Path,
) {

    companion object VelagonesCompanion {
        lateinit var plugin: VelagonesPlugin
        lateinit var proxyServer: ProxyServer
        lateinit var logger: Logger
        lateinit var dataDirectory: Path
    }

    init {
        VelagonesCompanion.proxyServer = proxyServer
        VelagonesCompanion.logger = logger
        VelagonesCompanion.plugin = this
        VelagonesCompanion.dataDirectory = dataDirectory
    }

    @Subscribe
    fun onInitialize(event: ProxyInitializeEvent) {
        // Forcibly use our springboot class loader
        val originalClassLoader = Thread.currentThread().contextClassLoader
        Thread.currentThread().contextClassLoader = VelagonesApplication::class.java.classLoader

        val app = SpringApplication(VelagonesApplication::class.java)
        app.addInitializers(
            ApplicationContextInitializer<ConfigurableApplicationContext> { ctx ->
                val env = ctx.environment
                val configFile = File(dataDirectory.toFile(), "application.properties")
                if (configFile.exists()) {
                    val properties =
                        PropertiesLoaderUtils.loadProperties(FileSystemResource(configFile))
                    val propertySource = PropertiesPropertySource("externalConfig", properties)
                    env.propertySources.addFirst(propertySource)
                    logger.info("Injected external configuration: ${configFile.path}")
                } else {
                    logger.info("External configuration not found: ${configFile.path}")
                }
            }
        )

        app.run()

        // Revert back to guice classloader
        Thread.currentThread().contextClassLoader = originalClassLoader
    }
}
