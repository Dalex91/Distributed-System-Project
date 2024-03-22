package com.example.repositories.monitoring

import com.example.dto.MeasurementDataResponseDto
import com.example.model.MeasurementData
import java.time.Instant

interface MonitoringManagementRepository {

    suspend fun findAll(): List<MeasurementData>

    suspend fun save(measurementData: MeasurementData): MeasurementData?

    suspend fun findByDeviceId(deviceId: Int): List<MeasurementData>

    suspend fun deleteByDeviceId(deviceId: Int): Int?

    suspend fun getConsumePerDay(deviceId: Int, date: Instant): List<MeasurementDataResponseDto>

    suspend fun updateOrInsertBetweenTimestamp(measurementData: MeasurementData): MeasurementData

    suspend fun updateHourlyConsumption(id: Int, newValue: Double)
}