package com.runicrealms.velagones.velocity.api.event

import com.runicrealms.velagones.velocity.api.autoscaler.Autoscaler
import com.runicrealms.velagones.velocity.api.selector.ServerSelector

/**
 * This event is fired while Velagones is initializing (which happens on proxy initialization). This
 * should be used to set custom autoscalers or server selectors.
 */
class VelagonesInitializeEvent {

    internal val customAutoscalers = HashMap<String, Autoscaler>()
    internal var customServerSelector: ServerSelector? = null

    /**
     * Register a custom autoscaler with a given name. To use the autoscaler, set
     *
     * type: custom custom: <autoscaler-name>
     *
     * Under the autoscaler field in the fleet configuration.
     */
    fun registerCustomAutoscaler(name: String, autoscaler: Autoscaler) {
        customAutoscalers[name] = autoscaler
    }

    /**
     * Register a custom server selector with a given name. To use the server selector, set
     *
     * type: custom
     *
     * Under the selector field in the config.
     */
    fun registerCustomServerSelector(selector: ServerSelector) {
        customServerSelector = selector
    }
}
