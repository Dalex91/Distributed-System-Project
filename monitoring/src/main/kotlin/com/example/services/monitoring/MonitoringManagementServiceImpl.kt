package com.example.services.monitoring

import com.example.dto.MeasurementDataResponseDto
import com.example.dto.MeasurementNotification
import com.example.dto.OverflowMeasurementResponse
import com.example.model.MeasurementData
import com.example.repositories.monitoring.MonitoringManagementRepository
import com.example.services.device.DeviceService
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.Ok
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.time.Instant

class MonitoringManagementServiceImpl(
    private val monitoringManagementRepository: MonitoringManagementRepository,
    private val deviceService: DeviceService
) : MonitoringManagementService {

    private val _notificationState: MutableSharedFlow<MeasurementNotification> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val notificationState = _notificationState.asSharedFlow()

    private suspend fun onChange(overflowValue: Double, deviceId: Int) {
        _notificationState.tryEmit(
            MeasurementNotification(
                data = OverflowMeasurementResponse(
                    deviceId = deviceId,
                    overflowValue = overflowValue
                )
            )
        )
    }

    override suspend fun findAll(): List<MeasurementData> =
        monitoringManagementRepository.findAll()

    override suspend fun findByDeviceId(deviceId: Int): Result<List<MeasurementData>, Unit> =
        Ok(monitoringManagementRepository.findByDeviceId(deviceId))

    override suspend fun save(measurementData: MeasurementData): Result<MeasurementData, Unit> =
        Ok(monitoringManagementRepository.updateOrInsertBetweenTimestamp(measurementData)).also {
            deviceService.findById(it.value.deviceId)?.let { device ->
                if (it.value.totalConsumption > device.maxHourlyEnergyConsumption)
                    onChange(
                        overflowValue = it.value.totalConsumption - device.maxHourlyEnergyConsumption,
                        deviceId = device.id
                    )
            }
        }

    override suspend fun deleteById(deviceId: Int): Result<Int, Unit> =
        monitoringManagementRepository.deleteByDeviceId(deviceId)?.let {
            Ok(it)
        } ?: Err(Unit)

    override suspend fun getConsumePerDay(deviceId: Int, date: Instant): List<MeasurementDataResponseDto> =
        monitoringManagementRepository.getConsumePerDay(deviceId, date)
}