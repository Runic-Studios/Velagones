package com.runicrealms.velagones.velocity

import com.runicrealms.velagones.service.DeactivateRequest
import com.runicrealms.velagones.service.DiscoverRequest
import com.runicrealms.velagones.service.VelagonesPaperGrpcKt
import com.runicrealms.velagones.velocity.api.VelagonesGameServer
import com.runicrealms.velagones.velocity.api.autoscaler.Autoscaler
import com.runicrealms.velagones.velocity.api.event.server.VelagonesDeactivateServerEvent
import com.runicrealms.velagones.velocity.api.event.server.VelagonesDiscoverServerEvent
import com.runicrealms.velagones.velocity.api.event.server.VelagonesRemoveServerEvent
import com.runicrealms.velagones.velocity.config.FleetConfig
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.server.ServerInfo
import io.grpc.ManagedChannelBuilder
import java.io.Closeable
import java.util.concurrent.ConcurrentHashMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.slf4j.Logger

/**
 * A group of PaperMC servers that are
 * - All recognized by Velagones
 * - Connected to the Velocity Proxy
 * - Discovered through Agones
 * - Are all in the same Agones fleet
 * - Can be autoscaled as a group
 */
class VelagonesFleet(
    private val proxy: ProxyServer,
    plugin: VelagonesPlugin,
    private val logger: Logger,
    config: FleetConfig,
    val name: String,
    val autoscaler: Autoscaler?,
) {

    init {
        proxy.eventManager.register(plugin, this)
    }

    val registry = Registry()

    val serverCapacity = config.serverCapacity

    val autoscalerEndpoint: AutoscalerEndpoint? =
        if (autoscaler == null) null
        else {
            AutoscalerEndpoint(proxy, plugin, logger, autoscaler, config.autoscaler, registry)
        }

    @Subscribe
    fun onProxyShutdown(event: ProxyShutdownEvent) {
        registry.close()
    }

    /** A registry of all PaperMC servers that we are communicating with */
    inner class Registry : Closeable {

        /** Maps game server names to their instances */
        internal val connected = ConcurrentHashMap<String, VelagonesGameServer>()

        /**
         * Sends a request to a potential server over gRPC to discover it. If successful, registers
         * the server both in the Velocity proxy and in our internal connected registry.
         *
         * Note that info should contain actual connection info (node IP, game port) while
         * grpcHost:grpcPort should be internal (pod IP, grpc port)
         */
        fun discover(info: ServerInfo, fleet: VelagonesFleet, grpcHost: String, grpcPort: Int) {
            val serverName = info.name
            val gameIp = info.address.address.hostAddress
            val gamePort = info.address.port

            val channel =
                ManagedChannelBuilder.forAddress(grpcHost, grpcPort).usePlaintext().build()
            val stub = VelagonesPaperGrpcKt.VelagonesPaperCoroutineStub(channel)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val request = DiscoverRequest.newBuilder().setServerName(info.name).build()

                    logger.info(
                        "Sending discovery message over gRPC to server with gRPC $grpcHost:$grpcPort named $serverName"
                    )
                    val response = stub.discover(request)
                    if (response.success) {
                        val registeredServer = proxy.registerServer(info)
                        val gameServer =
                            VelagonesGameServer(
                                registeredServer,
                                channel,
                                stub,
                                fleet.name,
                                fleet.serverCapacity,
                            )
                        connected[info.name] = gameServer
                        logger.info(
                            "Discovery successful: registering server with gRPC $grpcHost:$grpcPort and game $gameIp:$gamePort named $serverName in Velocity"
                        )
                        proxy.eventManager.fire(VelagonesDiscoverServerEvent(gameServer))
                    } else {
                        logger.warn(
                            "Server named $serverName with gRPC $grpcHost:$grpcPort did not want to be discovered over gRPC, skipping adding it"
                        )
                    }
                } catch (exception: Exception) {
                    logger.error(
                        "Failed to send discover message over gRPC to server $grpcHost:$grpcPort named $serverName",
                        exception,
                    )
                }
            }
        }

        /**
         * Sends a request to a server to deactivate it. If this request has immediate == true, then
         * the server is instructed to kick all players and shutdown immediately. If this request
         * has immediate = false, then the server waits until all players leave before shutting
         * down.
         *
         * When a server shuts down, it also marks itself as Shutdown in Agones.
         */
        fun deactivate(server: VelagonesGameServer, immediate: Boolean) {
            val name = server.registeredServer.serverInfo.name
            runBlocking {
                try {
                    withContext(Dispatchers.IO) {
                        // We can ignore the response
                        server.grpcStub.deactivate(
                            DeactivateRequest.newBuilder()
                                .setServerName(name)
                                .setImmediate(immediate)
                                .build()
                        )
                    }
                    server.deactivated = true
                    logger.info("Marked server $name as deactivated over gRPC")
                    proxy.eventManager.fire(VelagonesDeactivateServerEvent(server))
                } catch (exception: Exception) {
                    logger.error(
                        "Failed to send deactivate message over gRPC to server $name. We assume this server is unhealthy, attempting to remove it",
                        exception,
                    )
                    remove(server)
                }
            }
        }

        /**
         * Removes a registered server from both our internal registry and the Velocity proxy.
         * Assumes it has no players and has been deactivated.
         */
        fun remove(server: VelagonesGameServer) {
            val name = server.registeredServer.serverInfo.name
            val removed = connected.remove(server.registeredServer.serverInfo.name)
            if (removed == null) {
                logger.warn("Tried to remove server $name that does not exist")
                return
            }

            val info = server.registeredServer.serverInfo
            val serverIp = info.address.address.hostAddress
            val serverPort = info.address.port
            server.close()
            proxy.eventManager.fire(VelagonesRemoveServerEvent(server))
            logger.info("Unregistering server $name in Velocity $serverIp:$serverPort")
            proxy.unregisterServer(info)
        }

        override fun close() {
            for (server in connected.values) {
                remove(server)
            }
        }
    }
}
