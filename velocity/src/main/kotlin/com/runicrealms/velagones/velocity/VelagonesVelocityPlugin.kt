package com.runicrealms.velagones.velocity

import com.google.inject.Inject
import com.google.protobuf.Empty
import com.runicrealms.velagones.common.loadConfig
import com.runicrealms.velagones.discovery.DiscoveryServiceGrpc
import com.runicrealms.velagones.discovery.GameServer
import com.runicrealms.velagones.velocity.generated.model.VelagonesConfig
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.server.ServerInfo
import io.grpc.ServerBuilder
import io.grpc.stub.StreamObserver
import java.net.InetSocketAddress
import java.nio.file.Path
import org.slf4j.Logger

@Plugin(id = "Velagones")
class VelagonesVelocityPlugin
@Inject
constructor(
    private val server: ProxyServer,
    private val logger: Logger,
    @DataDirectory private val dataDirectory: Path,
) {

    // Read our config
    val config =
        loadConfig<VelagonesConfig>(
            dataDirectory.resolve("config.yml").toFile(),
            javaClass.getResourceAsStream("/config.yml"),
            logger,
        ) ?: run { throw IllegalStateException("Failed to load configuration!") }

    init {
        val discoveryPort = config.discovery.port
        server.scheduler
            .buildTask(
                this,
                Runnable {
                    val discoveryServer =
                        ServerBuilder.forPort(discoveryPort)
                            .addService(DiscoveryServiceImpl())
                            .build()
                            .start()
                    logger.info("Created discovery service gRPC server on port $discoveryPort")
                    discoveryServer.awaitTermination()
                },
            )
            .schedule()
    }

    inner class DiscoveryServiceImpl : DiscoveryServiceGrpc.DiscoveryServiceImplBase() {

        override fun join(request: GameServer?, responseObserver: StreamObserver<Empty>?) {
            request ?: return
            val address = InetSocketAddress(request.ip, request.port)
            server.registerServer(ServerInfo(request.name, address))
            responseObserver?.onNext(Empty.newBuilder().build())
            responseObserver?.onCompleted()
        }

        override fun leave(request: GameServer?, responseObserver: StreamObserver<Empty>?) {
            val paperServer = server.getServer(request?.name).orElse(null) ?: return
            server.unregisterServer(paperServer.serverInfo)
            responseObserver?.onNext(Empty.newBuilder().build())
            responseObserver?.onCompleted()
        }
    }
}
