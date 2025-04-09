package com.runicrealms.velagones.velocity.api.autoscaler

import com.runicrealms.velagones.velocity.config.DefaultAutoscalerConfig
import kotlin.math.ceil

/**
 * Default Velagones autoscaler.
 *
 * See wiki for more details.
 */
class DefaultAutoscaler(
    private val config: DefaultAutoscalerConfig,
    private val serverCapacity: Int,
    private val minServers: Int,
) : Autoscaler {

    private var lastScaleUp = 0L
    private var lastScaleDown = 0L

    override fun calculate(playerCount: Int, activeServerCount: Int): Int {
        val capacity = activeServerCount * serverCapacity

        val targetCapacity = ceil(config.capacityFactor * playerCount).toInt()
        val totalServersNeeded = ceil(targetCapacity.toDouble() / serverCapacity).toInt()

        val timeSinceScaleUp = (System.currentTimeMillis() - lastScaleUp) / 1000
        if (
            totalServersNeeded > activeServerCount &&
                timeSinceScaleUp >= config.up.minSecondsBefore &&
                capacity < targetCapacity
        ) {
            // SCALE UP
            lastScaleUp = System.currentTimeMillis()
            return totalServersNeeded
        }

        val timeSinceScaleDown = (System.currentTimeMillis() - lastScaleDown) / 1000
        if (
            timeSinceScaleDown >= config.down.minSecondsBefore &&
                capacity > config.capacityFactor * playerCount * config.down.hysteresis &&
                activeServerCount >= minServers
        ) {
            // SCALE DOWN
            val minCapacity = minServers * serverCapacity
            val requiredCapacity = ceil(config.capacityFactor * playerCount).toInt()
            val currentCapacity = activeServerCount * serverCapacity

            val maxRemoveableServers =
                (currentCapacity - maxOf(minCapacity, requiredCapacity)) / serverCapacity
            val serversToRemove = maxOf(0, maxRemoveableServers)

            lastScaleDown = System.currentTimeMillis()

            return activeServerCount - serversToRemove
        }

        return activeServerCount
    }
}
