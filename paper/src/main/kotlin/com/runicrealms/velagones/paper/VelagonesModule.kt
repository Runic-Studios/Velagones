package com.runicrealms.velagones.paper

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.google.inject.AbstractModule
import com.runicrealms.velagones.paper.config.VelagonesConfig
import jakarta.validation.Validation
import java.io.File
import org.slf4j.Logger

class VelagonesModule(private val plugin: VelagonesPlugin, private val logger: Logger) :
    AbstractModule() {

    override fun configure() {
        bind(VelagonesPlugin::class.java).toInstance(plugin)
        bind(Logger::class.java).toInstance(logger)

        // Load and bind config
        bind(VelagonesConfig::class.java).toInstance(loadConfig())

        bind(AgonesHook::class.java).asEagerSingleton()
        bind(VelagonesService::class.java).asEagerSingleton()
    }

    private fun loadConfig(): VelagonesConfig {
        val mapper =
            ObjectMapper(YAMLFactory())
                .registerModule(KotlinModule.Builder().build())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        val fallback = mapper.readTree(ClassLoader.getSystemResource("config.yml"))

        val configFile = File(plugin.dataFolder, "config.yml")
        val mergedNode =
            if (configFile.exists()) {
                logger.warn("No config.yml detected, falling back to defaults")
                val primary = mapper.readTree(configFile)
                fallback.deepCopy<JsonNode>().apply {
                    (this as ObjectNode).setAll<ObjectNode>(primary as ObjectNode)
                }
            } else fallback

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
