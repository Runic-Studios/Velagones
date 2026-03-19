package com.runicrealms.velagones.paper

import co.aikar.commands.BaseCommand
import co.aikar.commands.PaperCommandManager
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import com.google.inject.Inject
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.slf4j.Logger

@CommandAlias("velagones")
@CommandPermission("velagones.command")
class VelagonesCommand
@Inject
constructor(
    commandManager: PaperCommandManager,
    private val plugin: VelagonesPlugin,
    private val hook: AgonesHook,
    private val logger: Logger,
) : BaseCommand() {

    init {
        commandManager.registerCommand(this)
    }

    @Subcommand("shutdown")
    fun shutdown(sender: CommandSender) {
        runAgonesOp(sender, "shutdown") { hook.agones.shutdown() }
    }

    @Subcommand("ready")
    fun ready(sender: CommandSender) {
        runAgonesOp(sender, "ready") { hook.agones.ready() }
    }

    @Subcommand("allocate")
    fun allocate(sender: CommandSender) {
        runAgonesOp(sender, "allocate") { hook.agones.allocate() }
    }

    private fun runAgonesOp(sender: CommandSender, label: String, op: Runnable) {
        sender.sendMessage(
            Component.text("Requesting Agones SDK $label (async)...", NamedTextColor.GRAY)
        )
        Bukkit.getScheduler()
            .runTaskAsynchronously(
                plugin,
                Runnable {
                    try {
                        op.run()
                        Bukkit.getScheduler()
                            .runTask(
                                plugin,
                                Runnable {
                                    sender.sendMessage(
                                        Component.text(
                                            "Agones SDK $label completed.",
                                            NamedTextColor.GREEN,
                                        )
                                    )
                                },
                            )
                    } catch (throwable: Throwable) {
                        logger.warn("Agones SDK $label failed", throwable)
                        Bukkit.getScheduler()
                            .runTask(
                                plugin,
                                Runnable {
                                    sender.sendMessage(
                                        Component.text(
                                            "Agones SDK $label failed: ${throwable.message}",
                                            NamedTextColor.RED,
                                        )
                                    )
                                },
                            )
                    }
                },
            )
    }
}
