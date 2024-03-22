package device.example.repositories.device

import device.example.model.Device
import device.example.repositories.base.CrudRepository
import kotlinx.coroutines.flow.Flow

interface DeviceRepository : CrudRepository<Device, Int> {

    suspend fun findAllByUserId(id: Int): List<Device>

    suspend fun updateOnUserDelete(id: Int): Int?
}