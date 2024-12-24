package com.runicrealms.velagones.common

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import io.swagger.parser.OpenAPIParser
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.file.Files
import org.slf4j.Logger

inline fun <reified T> loadConfig(file: File, defaultConfig: InputStream?, logger: Logger): T? {
    // Copy default config
    if (!Files.exists(file.toPath())) {
        Files.createDirectories(file.parentFile.toPath())
        defaultConfig.use { defaultConfigStream ->
            if (defaultConfigStream != null) {
                Files.copy(defaultConfigStream, file.toPath())
            } else {
                throw IOException("Default config not found in resources!")
            }
        }
    }

    val yamlReader = ObjectMapper(YAMLFactory())
    val jsonWriter = ObjectMapper()

    try {
        val yamlTree: JsonNode = yamlReader.readTree(file)
        val jsonString = jsonWriter.writeValueAsString(yamlTree)

        // Parse + Validate config
        val result = OpenAPIParser().readContents(jsonString, null, null)

        if (result?.messages?.isNotEmpty() != null) {
            logger.warn("Failed to parse config:")
            result.messages.forEach(logger::warn)
            return null
        }

        // Load into pojo
        return jsonWriter.readValue(jsonString, T::class.java)
    } catch (exception: IOException) {
        logger.error("Failed to parse config:", exception)
    }
    return null
}
