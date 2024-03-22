package com.example.entity

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object MeasurementDataEntity : Table("measurements") {
    val id = integer("id").autoIncrement()
    val deviceId = integer("device_id")
    val startTimestamp = timestamp("start_timestamp")
    val endTimestamp = timestamp("end_timestamp")
    val totalConsumption = double("total_consumption")
    override val primaryKey = PrimaryKey(id)
}