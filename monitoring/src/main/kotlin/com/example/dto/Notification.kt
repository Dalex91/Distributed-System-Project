package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class Notification<T>(
    val message: String = "Overflow!",
    val data: T?
)

typealias MeasurementNotification = Notification<OverflowMeasurementResponse>