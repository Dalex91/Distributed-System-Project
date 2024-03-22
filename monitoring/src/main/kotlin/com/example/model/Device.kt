package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Device(
    val id: Int,
    val maxHourlyEnergyConsumption: Double
)
