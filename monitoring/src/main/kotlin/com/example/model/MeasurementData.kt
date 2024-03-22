package com.example.model

import java.time.Instant

data class MeasurementData(
    val id: Int = -1,
    val timestamp: Instant,
    val deviceId: Int,
    val totalConsumption: Double
)