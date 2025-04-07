package com.runicrealms.velagones.velocity

import com.google.inject.Inject
import com.typesafe.config.ConfigFactory
import com.velocitypowered.api.plugin.annotation.DataDirectory
import java.io.File
import java.nio.file.Path

@VelagonesComponent
class VelagonesConfig @Inject constructor(@DataDirectory val dataDirectory: Path) {

    private val externalConfig =
        ConfigFactory.parseFile(File(dataDirectory.toFile(), "application.conf"))

    // Falls back to jar resources config
    private val config =
        externalConfig.withFallback(ConfigFactory.parseResources("application.conf")).resolve()

    val agonesGameServerNamespace: String = config.getString("agones.gameServerNamespace")

    val agonesAutoscaleHostPort: Int = config.getInt("agones.autoscaleHostPort")

    val paperGrpcPort: Int = config.getInt("agones.paperGrpcPort")

    val scalingServerCapacity: Int =
        config.getInt("scaling.serverCapacity").also {
            if (it < 1)
                throw IllegalArgumentException("Config scaling.serverCapacity must be at least 0")
        }

    val scalingCapacityFactor: Double =
        config.getDouble("scaling.capacityFactor").also {
            if (it < 1.1 || it > 2.0)
                throw IllegalArgumentException(
                    "Config scaling.capacityFactor must between 1.1 and 2.0"
                )
        }

    val minServers: Int =
        config.getInt("scaling.minServers").also {
            if (it < 1)
                throw IllegalArgumentException("config scaling.minServers must be at least 1")
        }

    val scalingUpMinPlayersBefore: Int =
        config.getInt("scaling.up.minPlayersBefore").also {
            if (it < 0)
                throw IllegalArgumentException(
                    "Config scaling.up.minPlayersBefore must be at least 0"
                )
        }

    val scalingUpMinSecondsBefore: Int =
        config.getInt("scaling.up.minSecondsBefore").also {
            if (it < 0)
                throw IllegalArgumentException(
                    "Config scaling.up.minSecondsBefore must be at least 0"
                )
        }

    val scalingDownMinSecondsBefore: Int =
        config.getInt("scaling.down.minSecondsBefore").also {
            if (it < 0)
                throw IllegalArgumentException(
                    "Config scaling.down.minSecondsBefore must be at least 0"
                )
        }

    val scalingDownHysteresis: Double =
        config.getDouble("scaling.down.hysteresis").also {
            if (it < 1.0 || it > 1.5)
                throw IllegalArgumentException(
                    "Config scaling.down.hysteresis must be between 1.0 and 1.5"
                )
        }
}
