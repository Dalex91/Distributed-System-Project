package com.example.services.device

import com.example.model.Device
import com.example.repositories.device.DeviceRepository

class DeviceServiceImpl(
    private val deviceRepository: DeviceRepository
) : DeviceService {

    override suspend fun save(device: Device): Device? =
        deviceRepository.findById(device.id)?.let {
            null
        } ?: run {
            deviceRepository.save(device)
        }

    override suspend fun findById(id: Int): Device? =
        deviceRepository.findById(id)

    override suspend fun delete(id: Int): Int? =
        deviceRepository.delete(id)

    override suspend fun update(device: Device): Device? =
        deviceRepository.update(device)
}