package com.example.services.device

import com.example.model.Device

interface DeviceService {

    suspend fun save(device: Device): Device?

    suspend fun findById(id: Int): Device?

    suspend fun delete(id: Int): Int?

    suspend fun update(device: Device): Device?
}