package com.runicrealms.velagones.velocity.api.event

import com.runicrealms.velagones.velocity.api.VelagonesAPI

/**
 * This event is fired after Velagones initializes (which happens on proxy initialization). This
 * should only be used for getting the Velagones API.
 */
data class VelagonesInitializeEvent(val velagonesAPI: VelagonesAPI)
