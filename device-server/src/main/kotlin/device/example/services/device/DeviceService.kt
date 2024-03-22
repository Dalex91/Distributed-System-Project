package device.example.services.device

import com.github.michaelbull.result.Result
import device.example.errors.DeviceError
import device.example.model.Device
import kotlinx.coroutines.flow.Flow

interface DeviceService {

    suspend fun findAll(): List<Device>

    suspend fun findById(id: Int): Result<Device, DeviceError>

    suspend fun findAllByUserId(id: Int): List<Device>

    suspend fun save(device: Device): Result<Device, DeviceError>

    suspend fun update(device: Device): Result<Device, DeviceError>

    suspend fun delete(id: Int): Result<Int, DeviceError>

    suspend fun mapDeviceToUser(userId: Int, deviceId: Int): Result<Unit, DeviceError>
}