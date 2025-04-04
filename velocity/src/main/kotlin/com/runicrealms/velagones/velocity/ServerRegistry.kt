package com.runicrealms.velagones.velocity

import com.google.inject.Inject
import com.runicrealms.velagones.service.DeactivateRequest
import com.runicrealms.velagones.service.DiscoverRequest
import com.runicrealms.velagones.service.VelagonesPaperGrpcKt
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.server.ServerInfo
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import java.util.concurrent.ConcurrentHashMap
import kotlin.jvm.optionals.getOrNull
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.slf4j.Logger

/** A registry of all PaperMC servers that we are communicating with */
@VelagonesComponent
class ServerRegistry
@Inject
constructor(private val proxy: ProxyServer, private val logger: Logger) {

    // Maps name -> server
    val connected = ConcurrentHashMap<String, Server>()

    /**
     * Sends a request to a potential server over gRPC to discover it. If successful, registers the
     * server both in the Velocity proxy and in our internal connected registry.
     */
    fun discover(info: ServerInfo, grpcPort: Int) {
        val targetIp = info.address.hostName
        val serverName = info.name

        val channel = ManagedChannelBuilder.forAddress(targetIp, grpcPort).usePlaintext().build()
        val stub = VelagonesPaperGrpcKt.VelagonesPaperCoroutineStub(channel)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = DiscoverRequest.newBuilder().setServerName(info.name).build()

                logger.info(
                    "Sending discovery message over gRPC to server $targetIp:$grpcPort named $serverName"
                )
                val response = stub.discover(request)
                if (response.success) {
                    connected[serverName] = Server(info, channel, stub)
                    logger.info(
                        "Discovery successful: registering server $targetIp:${info.address.port} in Velocity"
                    )
                    proxy.registerServer(info)
                } else {
                    logger.warn(
                        "Server $targetIp:$grpcPort did not want to be discovered over gRPC, skipping adding it"
                    )
                }
            } catch (exception: Exception) {
                logger.error(
                    "Failed to send discover message over gRPC to server $targetIp:$grpcPort named $serverName"
                )
                exception.printStackTrace()
            }
        }
    }

    /**
     * Sends a request to a server to deactivate it. If this request has immediate == true, then the
     * server is instructed to kick all players and shutdown immediately. If this request has
     * immediate = false, then the server waits until all players leave before shutting down.
     *
     * When a server shuts down, it also marks itself as Shutdown in Agones.
     */
    fun deactivate(serverName: String, immediate: Boolean) {
        val server =
            connected[serverName]
                ?: throw IllegalArgumentException(
                    "Could not send deactivate to server $serverName, does not exist"
                )
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // We can ignore the response
                server.stub.deactivate(
                    DeactivateRequest.newBuilder()
                        .setServerName(serverName)
                        .setImmediate(immediate)
                        .build()
                )
                server.deactivated = true
            } catch (exception: Exception) {
                logger.error(
                    "Failed to send deactivate message over gRPC to server $serverName. We assume this server is unhealthy, attempting to remove it"
                )
                remove(serverName)
            }
        }
    }

    /**
     * Removes a registered server from both our internal registry and the Velocity proxy. Assumes
     * it has no players and has been deactivated.
     */
    fun remove(serverName: String) {
        val server = connected.remove(serverName)
        if (server == null) {
            logger.warn("Tried to remove server $serverName that does not exist")
            return
        }
        server.close()
        logger.info(
            "Unregistering server $serverName in Velocity ${server.info.address.hostName}:${server.info.address.port}"
        )
        proxy.unregisterServer(server.info)
    }

    /** Returns how many servers are currently not marked as deactivated. */
    fun amountActive() = connected.values.filter { !it.deactivated }.size

    /** Returns how many players are connected across all servers (active or not). */
    fun playerCount() =
        connected.values.sumOf {
            proxy.getServer(it.info.name).getOrNull()?.playersConnected?.size ?: 0
        }

    /** Represents a Velagones-Aware server we have connected the proxy to. */
    data class Server(
        val info: ServerInfo,
        val channel: ManagedChannel,
        val stub: VelagonesPaperGrpcKt.VelagonesPaperCoroutineStub,
    ) {

        var deactivated = false

        fun close() {
            channel.shutdown()
        }
    }
}
