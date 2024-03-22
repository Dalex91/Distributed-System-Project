package com.example.repositories.device

import com.example.model.Device

interface DeviceRepository {

    suspend fun save(device: Device): Device?

    suspend fun delete(id: Int): Int?

    suspend fun findById(id: Int): Device?

    suspend fun update(device: Device): Device?
}