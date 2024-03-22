package device.example.model

import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class Device(
    val id: Int = -1,
    val userId: Int = -1,
    val description: String,
    val address: String,
    val maxHourlyEnergyConsumption: Int
)