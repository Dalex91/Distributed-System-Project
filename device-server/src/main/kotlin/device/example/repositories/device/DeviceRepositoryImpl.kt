package device.example.repositories.device

import device.example.entities.DeviceEntity
import device.example.mappers.toDeviceModel
import device.example.model.Device
import device.example.repositories.database.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DeviceRepositoryImpl : DeviceRepository {

    override suspend fun save(entity: Device): Device? = dbQuery {
        DeviceEntity.insert {
            entity.apply {
                it[DeviceEntity.address] = address
                it[DeviceEntity.maxHourlyEnergyConsumption] = maxHourlyEnergyConsumption
                it[DeviceEntity.description] = description
                it[DeviceEntity.userId] = userId
            }
        }.resultedValues?.singleOrNull()?.toDeviceModel()
    }

    override suspend fun findAllByUserId(id: Int): List<Device> = dbQuery {
        DeviceEntity
            .select { DeviceEntity.userId eq id }
            .map { it.toDeviceModel() }
    }

    override suspend fun findAll(): List<Device> = dbQuery {
        DeviceEntity
            .selectAll()
            .map {
                it.toDeviceModel()
            }
    }

    override suspend fun findById(id: Int): Device? = dbQuery {
        DeviceEntity
            .select(DeviceEntity.id eq id).map { it.toDeviceModel() }
            .singleOrNull()
    }

    override suspend fun delete(id: Int): Int? = dbQuery {
        if (DeviceEntity.deleteWhere { this.id eq id } == 1)
            id
        else
            null
    }

    override suspend fun update(entity: Device): Device? = dbQuery {
        if (
            DeviceEntity.update({ DeviceEntity.id eq entity.id }) {
                entity.apply {
                    if (userId != -1)
                        it[DeviceEntity.userId] = userId
                    it[DeviceEntity.address] = address
                    it[DeviceEntity.description] = description
                    it[DeviceEntity.maxHourlyEnergyConsumption] = maxHourlyEnergyConsumption
                }
            } == 1
        )
            entity
        else
            null
    }

    override suspend fun updateOnUserDelete(id: Int) = dbQuery {
        if (
            DeviceEntity.update({ DeviceEntity.userId eq id }) {
                it[DeviceEntity.userId] = -1
            } > 0
        )
            id
        else
            null
    }

    override suspend fun deleteAll() {
        DeviceEntity.deleteAll()
    }
}