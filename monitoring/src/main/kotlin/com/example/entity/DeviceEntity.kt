package com.example.entity

import org.jetbrains.exposed.sql.Table

object DeviceEntity : Table("devices") {
    val id = integer("id").uniqueIndex()
    val maxHourlyEnergyConsumption = double("maximum_hourly_energy_consumption")
    override val primaryKey = PrimaryKey(id)
}