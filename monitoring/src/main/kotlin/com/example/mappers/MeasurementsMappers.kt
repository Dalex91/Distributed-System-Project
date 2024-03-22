package com.example.mappers

import com.example.dto.MeasurementDataDto
import com.example.dto.MeasurementDataResponseDto
import com.example.entity.DeviceEntity
import com.example.entity.MeasurementDataEntity
import com.example.model.Device
import com.example.model.MeasurementData
import com.example.util.formatInstantToHourMinute
import org.jetbrains.exposed.sql.ResultRow
import java.time.Instant

fun ResultRow.toModel() =
    MeasurementData(
        id = this[MeasurementDataEntity.id],
        deviceId = this[MeasurementDataEntity.deviceId],
        timestamp = this[MeasurementDataEntity.startTimestamp],
        totalConsumption = this[MeasurementDataEntity.totalConsumption]
    )

fun ResultRow.toDeviceModel() =
    Device(
        id = this[DeviceEntity.id],
        maxHourlyEnergyConsumption = this[DeviceEntity.maxHourlyEnergyConsumption],
    )

fun ResultRow.toResponse() =
    MeasurementDataResponseDto(
        totalConsumption = this[MeasurementDataEntity.totalConsumption],
        endHour = formatInstantToHourMinute(this[MeasurementDataEntity.endTimestamp]),
        startHour = formatInstantToHourMinute(this[MeasurementDataEntity.startTimestamp])
    )

fun MeasurementDataDto.toModel() =
    MeasurementData(
        deviceId = this.deviceId,
        timestamp = Instant.ofEpochMilli(this.timestamp),
        totalConsumption = this.measurementValue
    )
