package com.runicrealms.velagones

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

    val gameServerNamespace: String = config.getString("velagones.game-server-namespace")

    val maxPlayersPerServer: Int = config.getInt("velagones.max-players-per-server")
}
