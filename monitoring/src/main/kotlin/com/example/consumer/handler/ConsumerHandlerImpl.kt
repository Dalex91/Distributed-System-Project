package com.example.consumer.handler

import com.example.model.Device
import com.example.model.MeasurementData
import com.example.services.device.DeviceService
import com.example.services.monitoring.MonitoringManagementService
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess

class ConsumerHandlerImpl(
    private val monitoringManagementService: MonitoringManagementService,
    private val deviceService: DeviceService
) : ConsumerHandler {

    override suspend fun saveMeasurement(measurementData: MeasurementData) {
        monitoringManagementService.save(measurementData)
            .onSuccess {
                println("All good! ${measurementData.toString()}")
            }
            .onFailure {
                println("Something went wrong with ${measurementData.toString()}")
            }
    }

    override suspend fun saveDevice(device: Device) {
        deviceService.save(device)
        println("Device ${device.toString()} saved!")
    }

    override suspend fun updateDevice(device: Device) {
        deviceService.update(device)
        println("Device ${device.toString()} updated!")
    }

    override suspend fun deleteDevice(id: Int) {
        deviceService.delete(id)
        println("Device with id $id deleted!")
    }
}