package com.runicrealms.velagones.velocity.api

import com.runicrealms.velagones.service.VelagonesPaperGrpcKt
import com.velocitypowered.api.proxy.server.RegisteredServer
import io.grpc.ManagedChannel
import java.io.Closeable

/**
 * Represents a Velagones-Aware paper server we have connected the proxy to. Its existence implies
 * that it:
 * - Has been discovered successfully by Velagones
 * - Is currently healthy in Agones
 * - Is connected to the Velocity proxy
 *
 * @param registeredServer The RegisteredServer in the Velocity proxy
 * @param grpcChannel The ManagedChannel for communicating to this server over gRPC
 * @param grpcStub The stub we have started
 * @param fleet The name of the Agones Fleet it belongs to
 * @param capacity Maximum number of players that can connect to it
 */
data class VelagonesGameServer(
    val registeredServer: RegisteredServer,
    private val grpcChannel: ManagedChannel,
    internal val grpcStub: VelagonesPaperGrpcKt.VelagonesPaperCoroutineStub,
    val fleet: String,
    val capacity: Int,
) : Closeable {
    /**
     * Represents whether this server is currently receiving players.
     *
     * If a server is deactivated, it may still have players connected, but should not have new
     * connections (and be waiting for existing ones to leave so that it can shut down).
     */
    var deactivated = false
        internal set

    override fun close() {
        grpcChannel.shutdown()
    }
}
