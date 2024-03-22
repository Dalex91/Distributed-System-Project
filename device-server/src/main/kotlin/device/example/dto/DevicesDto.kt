package device.example.dto

import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class DeviceDto(
    val id: Int,
    val description: String,
    val address: String,
    val maxHourlyEnergyConsumption: Int
)

@Serializable
data class CreateDeviceDto(
    val id: Int,
    val description: String,
    val address: String,
    val maxHourlyEnergyConsumption: Int
)

@Serializable
data class UpdateDeviceDto(
    val id: Int,
    val description: String,
    val address: String,
    val maxHourlyEnergyConsumption: Int
)

@Serializable
data class DeviceAndUserIdsDto(
    val userId: Int,
    val deviceId: Int
)
