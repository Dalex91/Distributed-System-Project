package com.example.services.monitoring

import com.example.dto.MeasurementDataResponseDto
import com.example.dto.MeasurementNotification
import com.example.model.MeasurementData
import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.SharedFlow
import java.time.Instant

interface MonitoringManagementService {

    suspend fun findAll(): List<MeasurementData>

    suspend fun save(measurementData: MeasurementData): Result<MeasurementData, Unit>

    suspend fun findByDeviceId(deviceId: Int): Result<List<MeasurementData>, Unit>

    suspend fun deleteById(deviceId: Int): Result<Int, Unit>

    suspend fun getConsumePerDay(deviceId: Int, date: Instant): List<MeasurementDataResponseDto>

    val notificationState: SharedFlow<MeasurementNotification>
}