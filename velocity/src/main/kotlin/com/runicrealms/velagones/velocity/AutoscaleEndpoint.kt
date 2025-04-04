package com.runicrealms.velagones.velocity

import com.google.inject.Inject
import com.runicrealms.velagones.velocity.generated.autoscaler.model.AutoscaleRequestModel
import com.runicrealms.velagones.velocity.generated.autoscaler.model.AutoscaleResponseModel
import com.runicrealms.velagones.velocity.generated.autoscaler.model.AutoscaleResponseModelResponse
import com.velocitypowered.api.proxy.ProxyServer
import io.javalin.Javalin
import org.slf4j.Logger

@VelagonesComponent
class AutoscaleEndpoint
@Inject
constructor(
    proxy: ProxyServer,
    plugin: VelagonesPlugin,
    private val logger: Logger,
    private val config: VelagonesConfig,
    private val connectionHandler: ConnectionHandler,
) {

    init {
        proxy.scheduler.buildTask(plugin, Runnable { startJavalin() })
    }

    private fun startJavalin() {
        logger.info(
            "Starting Javalin server for /autoscale on port ${config.agonesAutoscaleHostPort}"
        )
        val app = Javalin.create().start(config.agonesAutoscaleHostPort)
        app.get("/autoscale") { ctx ->
            val request = ctx.bodyAsClass(AutoscaleRequestModel::class.java)

            val response =
                AutoscaleResponseModel()
                    .response(
                        AutoscaleResponseModelResponse()
                            .uid(request.request.uid)
                            .replicas(connectionHandler.fleetSize)
                            .scale(true)
                    )

            logger.info(
                "Received autoscale request from Agones, responding with target ${connectionHandler.fleetSize}"
            )

            ctx.json(response)
        }
    }
}
