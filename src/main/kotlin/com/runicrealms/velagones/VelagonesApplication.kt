package com.runicrealms.velagones

import io.k8swatcher.annotation.EnableInformer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties

@SpringBootApplication
@EnableConfigurationProperties(VelagonesConfig::class)
@EnableInformer
class VelagonesApplication
