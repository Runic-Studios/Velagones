package com.runicrealms.velagones.paper

import com.google.inject.Inject
import com.typesafe.config.ConfigFactory
import java.io.File

@VelagonesComponent
class VelagonesConfig @Inject constructor(plugin: VelagonesPlugin) {

    private val externalConfig =
        ConfigFactory.parseFile(File(plugin.dataFolder, "application.conf"))

    // Falls back to jar resources config
    private val config =
        externalConfig.withFallback(ConfigFactory.parseResources("application.conf")).resolve()

    val servicePort: Int = config.getInt("service.hostPort")
}
