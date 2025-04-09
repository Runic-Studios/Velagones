package com.runicrealms.velagones.velocity

import com.runicrealms.velagones.velocity.api.autoscaler.Autoscaler
import com.runicrealms.velagones.velocity.config.AutoscalerConfig
import com.runicrealms.velagones.velocity.generated.autoscaler.model.AutoscaleRequestModel
import com.runicrealms.velagones.velocity.generated.autoscaler.model.AutoscaleResponseModel
import com.runicrealms.velagones.velocity.generated.autoscaler.model.AutoscaleResponseModelResponse
import com.velocitypowered.api.proxy.ProxyServer
import io.javalin.Javalin
import org.slf4j.Logger

class AutoscalerEndpoint(
    proxy: ProxyServer,
    plugin: VelagonesPlugin,
    private val logger: Logger,
    private val autoscaler: Autoscaler,
    private val autoscalerConfig: AutoscalerConfig,
    private val registry: ServerGroup.Registry,
) {

    private val port = autoscalerConfig.hostPort ?: throw IllegalArgumentException("Missing config: autoscaler.hostPort")

    init {
        proxy.scheduler.buildTask(plugin, Runnable { startJavalin() })
    }

    private fun startJavalin() {
        logger.info("Starting Javalin server for /autoscale on port $port")
        val app = Javalin.create().start(port)
        app.get("/autoscale") { ctx ->
            val request = ctx.bodyAsClass(AutoscaleRequestModel::class.java)

            synchronized(this) {
                val active = registry.connected.values.filter { !it.deactivated }.size
                val deactivated = registry.connected.values.filter { it.deactivated }.size
                val target = autoscale(active) + deactivated

                val response =
                    AutoscaleResponseModel()
                        .response(
                            AutoscaleResponseModelResponse()
                                .uid(request.request.uid)
                                .replicas(target)
                                .scale(true)
                        )

                logger.info(
                    "Received autoscale request from Agones, responding with target $target"
                )

                ctx.json(response)
            }
        }
    }

    private fun autoscale(fleetSize: Int): Int {
        val playerCount =
            registry.connected.values.sumOf { it.registeredServer.playersConnected.size }
        val activeServerCount = registry.connected.values.filter { !it.deactivated }.size

        val target = autoscaler.calculate(playerCount, activeServerCount)

        val minServers = autoscalerConfig.minServers

        if (fleetSize < minServers) {
            logger.info("Scaling up to $minServers to reach minimum servers")
            return minServers
        }

        if (target < fleetSize) {
            val deactivateAmount = fleetSize - target
            val serversSorted =
                registry.connected.values
                    .filter { !it.deactivated }
                    .sortedBy { it.registeredServer.playersConnected.size }
            for (i in 0 until deactivateAmount) {
                if (i >= serversSorted.size) {
                    logger.warn("Cannot deactivate $deactivateAmount servers: not enough servers")
                    return fleetSize
                }
                val server = serversSorted[i]
                val name = server.registeredServer.serverInfo.name
                logger.info("Deactivating server $name due to scale down")
                registry.deactivate(server, false)
            }
            return fleetSize - deactivateAmount
        } else if (target > fleetSize) {
            logger.info("Scaling up to $target target servers")
            return target
        }

        return fleetSize
    }
}
