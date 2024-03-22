package com.example.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializable
data class MeasurementDataDto(
    @SerialName("timestamp") val timestamp: Long,
    @SerialName("device_id") val deviceId: Int,
    @SerialName("measurement_value") val measurementValue: Double
)

@Serializable
data class OverflowMeasurementResponse(
    val deviceId: Int,
    val time: String = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
    val overflowValue: Double
)

@Serializable
data class HourlyConsumePerDayDto(
    @SerialName("device_id") val deviceId: Int,
    @SerialName("date") val date: String
)

@Serializable
data class MeasurementDataResponseDto(
    val startHour: String,
    val endHour: String,
    val totalConsumption: Double
)