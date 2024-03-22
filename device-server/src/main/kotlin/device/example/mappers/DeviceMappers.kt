package device.example.mappers

import device.example.dto.CreateDeviceDto
import device.example.dto.DeviceDto
import device.example.dto.UpdateDeviceDto
import device.example.entities.DeviceEntity
import device.example.entities.UserEntity
import device.example.model.Device
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toDeviceModel() =
    Device(
        id = this[DeviceEntity.id],
        userId = this[DeviceEntity.userId],
        description = this[DeviceEntity.description],
        maxHourlyEnergyConsumption = this[DeviceEntity.maxHourlyEnergyConsumption],
        address = this[DeviceEntity.address]
    )

fun ResultRow.toUserModel() = this[UserEntity.userID]

fun CreateDeviceDto.toDeviceModel() =
    Device(
        description = this.description,
        maxHourlyEnergyConsumption = this.maxHourlyEnergyConsumption,
        address = this.address
    )

fun UpdateDeviceDto.toDeviceModel() =
    Device(
        id = this.id,
        description = this.description,
        maxHourlyEnergyConsumption = this.maxHourlyEnergyConsumption,
        address = this.address
    )

fun Device.toDto() =
    DeviceDto(
        id = this.id,
        description = this.description,
        address = this.address,
        maxHourlyEnergyConsumption = this.maxHourlyEnergyConsumption
    )