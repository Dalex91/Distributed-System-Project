package com.example.repositories.device

import com.example.model.Device
import com.example.database.DatabaseFactory.dbQuery
import com.example.entity.DeviceEntity
import com.example.mappers.toDeviceModel
import com.example.repositories.monitoring.MonitoringManagementRepository
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update

class DeviceRepositoryImpl(
    private val monitoringManagementRepository: MonitoringManagementRepository
) : DeviceRepository {
    override suspend fun save(device: Device): Device? = dbQuery {
        DeviceEntity.insert {
            it[DeviceEntity.id] = device.id
            it[DeviceEntity.maxHourlyEnergyConsumption] = device.maxHourlyEnergyConsumption
        }.resultedValues?.singleOrNull()?.toDeviceModel()
    }

    override suspend fun delete(id: Int): Int? = dbQuery {
        if (DeviceEntity.deleteWhere { this.id eq id } == 1) {
            monitoringManagementRepository.deleteByDeviceId(id)
            id
        } else
            null
    }

    override suspend fun findById(id: Int): Device? = dbQuery {
        DeviceEntity
            .select { DeviceEntity.id eq id }.map { it.toDeviceModel() }
            .singleOrNull()
    }

    override suspend fun update(device: Device): Device? = dbQuery {
        if (DeviceEntity.update({ DeviceEntity.id eq device.id }) {
                it[DeviceEntity.maxHourlyEnergyConsumption] = device.maxHourlyEnergyConsumption
            } == 1
        )
            device
        else
            null
    }
}