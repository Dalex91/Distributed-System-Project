package device.example.entities

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object DeviceEntity : Table("devices") {
    val id = integer("id")
        .autoIncrement()
    val userId = integer("userId")
    val description = text("description")
    val address = text("address")
    val maxHourlyEnergyConsumption = integer("maximum_hourly_energy_consumption")
    override val primaryKey = PrimaryKey(id)
}