package com.example.consumer.handler

import com.example.model.Device
import com.example.model.MeasurementData

interface ConsumerHandler {
    suspend fun saveMeasurement(measurementData: MeasurementData)
    suspend fun saveDevice(device: Device)
    suspend fun updateDevice(device: Device)
    suspend fun deleteDevice(id: Int)
}