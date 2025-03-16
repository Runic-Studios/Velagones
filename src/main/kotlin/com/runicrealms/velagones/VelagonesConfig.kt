package com.runicrealms.velagones

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean

@ConfigurationProperties(prefix = "velagones")
data class VelagonesConfig(var gameServerNamespace: String? = null) {

    @Bean fun proxyServer() = VelagonesPlugin.proxyServer

    @Bean fun logger() = VelagonesPlugin.logger

    @Bean fun plugin() = VelagonesPlugin.plugin
}
