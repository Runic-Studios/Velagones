package com.runicrealms.velagones.velocity.api.event

import com.google.inject.Module

/**
 * This event is fired while Velagones is initializing (which happens on proxy initialization). This
 * should be used to inject custom Guice module overrides for the existing Velagones API. Uses
 * include:
 * - Injecting a custom ServerSelector (required if selector.type is set to custom)
 * - Injecting a custom Autoscaler (provided autoscaler.type is set to custom)
 */
class VelagonesInjectEvent {

    val overrides = mutableListOf<Module>()
}
