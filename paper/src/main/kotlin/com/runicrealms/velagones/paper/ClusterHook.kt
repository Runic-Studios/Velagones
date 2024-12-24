package com.runicrealms.velagones.paper

import com.google.inject.Inject
import com.runicrealms.velagones.discovery.DiscoveryServiceGrpc
import com.runicrealms.velagones.discovery.GameServer
import com.runicrealms.velagones.paper.generated.model.VelagonesConfig
import io.grpc.ManagedChannelBuilder
import io.kubernetes.client.openapi.Configuration
import io.kubernetes.client.openapi.apis.CoreV1Api
import io.kubernetes.client.util.ClientBuilder
import java.time.Duration
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.future.await
import kotlinx.coroutines.runBlocking
import net.infumia.agones4j.Agones
import org.slf4j.Logger

class ClusterHook
@Inject
constructor(private val plugin: VelagonesPaperPlugin, logger: Logger, config: VelagonesConfig) {

    val agones: Agones
    val discoveryStub: DiscoveryServiceGrpc.DiscoveryServiceStub
    var gameServer: GameServer

    init {
        logger.info("Starting Agones hook...")
        // Construct agones SDK wrapper on GRPC
        val agonesPort = config.agones.localPort
        val gameServerWatcherExecutor = Executors.newSingleThreadExecutor()
        val healthCheckExecutor = Executors.newSingleThreadScheduledExecutor()
        agones =
            Agones.builder()
                .withAddress("localhost", agonesPort)
                .withChannel(
                    ManagedChannelBuilder.forAddress("localhost", agonesPort).usePlaintext().build()
                )
                .withGameServerWatcherExecutor(gameServerWatcherExecutor)
                .withHealthCheck(
                    Duration.ofSeconds(1L), // Delay
                    Duration.ofSeconds(2L), // Period
                )
                .withHealthCheckExecutor(healthCheckExecutor)
                .build()
        logger.info("Instantiated Agones hook on localhost:{}", agonesPort)
        if (agones.canHealthCheck()) {
            agones.startHealthChecking()
            logger.info("Began Agones health checking")
        } else {
            throw IllegalStateException("Failed to begin Agones health checking")
        }
        if (agones.canWatchGameServer()) {
            agones.addGameServerWatcher {
                logger.info("Received state updated from Agones: {}", it.status.state)
            }
        } else {
            logger.warn("Failed to add game server watcher: Not allowed")
        }
        // Get gameserver
        runBlocking {
            val sdkGameServer = agones.gameServerFuture.await()
            gameServer =
                GameServer.newBuilder()
                    .setName(sdkGameServer.objectMeta.name)
                    .setPort(sdkGameServer.status.getPorts(0).port)
                    .setIp(sdkGameServer.status.address)
                    .build()
        }
        logger.info("Retrieved Agones GameServer information {}", gameServer)

        // Create K8s cluster api
        val client = ClientBuilder.defaultClient()
        Configuration.setDefaultApiClient(client)
        logger.info("Initialized K8s cluster API client")
        val coreApi = CoreV1Api()

        // Load clusterIP for Agones allocator service
        val service =
            coreApi
                .readNamespacedService(
                    config.discovery.service.name,
                    config.discovery.service.namespace,
                )
                .execute()
        val serviceIp = service.spec.clusterIP
        val servicePort = service.spec.ports[0].port
        logger.info(
            "Found discovery service named {} in namespace {} found cluster target {}:{}",
            config.discovery.service.name,
            config.discovery.service.namespace,
            serviceIp,
            servicePort,
        )

        // Create ManagedChannel
        val managedChannel =
            ManagedChannelBuilder.forAddress(serviceIp, servicePort)
                .usePlaintext()
                .enableRetry()
                .keepAliveTime(10, TimeUnit.SECONDS)
                .build()
        logger.info("Opened gRPC channel with Velagones Discovery Service")

        // Return DiscoveryServiceGrpc stub
        discoveryStub = DiscoveryServiceGrpc.newStub(managedChannel)
    }
}
