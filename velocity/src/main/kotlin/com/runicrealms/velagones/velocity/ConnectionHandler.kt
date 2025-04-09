package com.runicrealms.velagones.velocity

import com.google.inject.Inject
import com.runicrealms.velagones.velocity.api.selector.ServerSelector
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent
import com.velocitypowered.api.proxy.ProxyServer
import net.kyori.adventure.text.Component
import org.slf4j.Logger

class ConnectionHandler
@Inject
constructor(
    plugin: VelagonesPlugin,
    proxy: ProxyServer,
    private val logger: Logger,
    private val fleetRegistry: VelagonesFleetRegistry,
    private val serverSelector: ServerSelector,
) {

    init {
        proxy.eventManager.register(plugin, this)
    }

    @Subscribe
    fun onServerPreConnect(event: PlayerChooseInitialServerEvent) {
        val servers = fleetRegistry.fleets.values.map { it.registry.connected.values }.flatten()

        val selectionResponse = serverSelector.selectServer(event.player, servers)

        if (selectionResponse.server == null) {
            logger.info(
                "Denying player ${event.player.uniqueId} entry: ${selectionResponse.kickMessage}"
            )
            event.player.disconnect(Component.text(selectionResponse.kickMessage))
            event.setInitialServer(null)
        } else {
            val name = selectionResponse.server.registeredServer.serverInfo.name
            "Intercepted login from player ${event.player.uniqueId}, sending to $name"
            event.setInitialServer(selectionResponse.server.registeredServer)
        }
    }
}
