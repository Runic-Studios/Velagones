package com.runicrealms.velagones.velocity

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.google.inject.AbstractModule
import com.runicrealms.velagones.velocity.api.selector.DistributedServerSelector
import com.runicrealms.velagones.velocity.api.selector.PackedServerSelector
import com.runicrealms.velagones.velocity.api.selector.ServerSelector
import com.runicrealms.velagones.velocity.config.*
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import jakarta.validation.Validation
import java.io.File
import java.nio.file.Path
import org.slf4j.Logger

class VelagonesModule(
    private val plugin: VelagonesPlugin,
    private val proxy: ProxyServer,
    private val logger: Logger,
    private val dataDirectory: Path,
) : AbstractModule() {

    override fun configure() {
        // Bind Velocity
        bind(VelagonesPlugin::class.java).toInstance(plugin)
        bind(ProxyServer::class.java).toInstance(proxy)
        bind(Logger::class.java).toInstance(logger)
        bind(Path::class.java).annotatedWith(DataDirectory::class.java).toInstance(dataDirectory)

        // Load and bind config
        val config = loadConfig()
        bind(VelagonesConfig::class.java).toInstance(config)

        // Bind Default API implementations
        when (config.selector.type!!) {
            SelectorConfig.Type.DISTRIBUTED ->
                bind(ServerSelector::class.java).to(DistributedServerSelector::class.java)
            SelectorConfig.Type.PACKED ->
                bind(ServerSelector::class.java).to(PackedServerSelector::class.java)
            SelectorConfig.Type.CUSTOM ->
                logger.info("Detected \"custom\" selector.type, not injecting any selector.")
        }

        // Bind Velagones classes
        bind(VelagonesFleetRegistry::class.java).asEagerSingleton()
        bind(ClusterWatcher::class.java).asEagerSingleton()
        bind(ConnectionHandler::class.java).asEagerSingleton()
    }

    private fun loadConfig(): VelagonesConfig {
        val mapper =
            ObjectMapper(YAMLFactory())
                .registerModule(KotlinModule.Builder().build())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        val fallback = mapper.readTree(javaClass.classLoader.getResource("config.fallback.yml"))

        val configFile = File(dataDirectory.toFile(), "config.fallback.yml")
        val mergedNode =
            if (configFile.exists()) {
                val primary = mapper.readTree(configFile)
                fallback.deepCopy<JsonNode>().apply {
                    (this as ObjectNode).setAll<ObjectNode>(primary as ObjectNode)
                }
            } else {
                logger.warn("No config.fallback.yml detected, falling back to defaults")
                fallback
            }

        val merged = mapper.treeToValue(mergedNode, VelagonesConfig::class.java)

        logger.info("Loaded merged config: $merged")

        val validator = Validation.buildDefaultValidatorFactory().validator
        val violations = validator.validate(merged)

        if (violations.isNotEmpty()) {
            val sb = StringBuilder("Validation failed:\n")
            for (violation in violations) {
                sb.append("${violation.propertyPath}: ${violation.message}\n")
            }
            throw IllegalArgumentException(sb.toString())
        }

        logger.info("Successfully validated config")

        return merged
    }
}
