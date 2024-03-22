package com.example.repositories.monitoring

import com.example.database.DatabaseFactory.dbQuery
import com.example.dto.MeasurementDataResponseDto
import com.example.entity.MeasurementDataEntity
import com.example.mappers.toModel
import com.example.mappers.toResponse
import com.example.model.MeasurementData
import com.example.util.areSameDate
import com.example.util.calculateEndTimestamp
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.time.Instant

class MonitoringManagementRepositoryImpl : MonitoringManagementRepository {

    override suspend fun save(measurementData: MeasurementData): MeasurementData? = dbQuery {
        MeasurementDataEntity.insert {
            it[MeasurementDataEntity.deviceId] = measurementData.deviceId
            it[MeasurementDataEntity.totalConsumption] = measurementData.totalConsumption
            it[MeasurementDataEntity.startTimestamp] = measurementData.timestamp
            it[MeasurementDataEntity.endTimestamp] = calculateEndTimestamp(measurementData.timestamp)
        }
    }.resultedValues?.singleOrNull()?.toModel()


    override suspend fun findAll(): List<MeasurementData> = dbQuery {
        MeasurementDataEntity
            .selectAll()
            .map {
                it.toModel()
            }
    }

    override suspend fun findByDeviceId(deviceId: Int): List<MeasurementData> = dbQuery {
        MeasurementDataEntity
            .select(MeasurementDataEntity.deviceId eq deviceId)
            .map {
                it.toModel()
            }
    }

    override suspend fun getConsumePerDay(deviceId: Int, date: Instant): List<MeasurementDataResponseDto> = dbQuery {
        MeasurementDataEntity
            .select {
                MeasurementDataEntity.deviceId eq deviceId
            }
            .filter {
                areSameDate(it[MeasurementDataEntity.startTimestamp], date)
            }
            .map {
                it.toResponse()
            }
    }

    override suspend fun updateHourlyConsumption(id: Int, newValue: Double) = dbQuery {
        MeasurementDataEntity.update({ MeasurementDataEntity.id eq id }) {
            it[MeasurementDataEntity.totalConsumption] = newValue
        }
        return@dbQuery
    }

    override suspend fun updateOrInsertBetweenTimestamp(measurementData: MeasurementData): MeasurementData = dbQuery {
        MeasurementDataEntity
            .select {
                (MeasurementDataEntity.deviceId eq measurementData.deviceId) and
                        (MeasurementDataEntity.startTimestamp lessEq measurementData.timestamp) and
                        (MeasurementDataEntity.endTimestamp greaterEq measurementData.timestamp)
            }
            .singleOrNull()?.let {
                val resultedValue = it.toModel()
                updateHourlyConsumption(
                    resultedValue.id,
                    resultedValue.totalConsumption + measurementData.totalConsumption
                )
            } ?: run {
            save(measurementData)
        }
        measurementData
    }

    override suspend fun deleteByDeviceId(deviceId: Int): Int? = dbQuery {
        if (
            MeasurementDataEntity.deleteWhere { MeasurementDataEntity.deviceId eq deviceId } != 0
        )
            deviceId
        else
            null
    }
}