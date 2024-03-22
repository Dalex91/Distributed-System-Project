package device.example.services.device

import com.github.michaelbull.result.*
import device.example.errors.DeviceError
import device.example.model.Device
import device.example.repositories.device.DeviceRepository
import device.example.repositories.user.UserRepository

class DeviceServiceImpl(
    private val deviceRepository: DeviceRepository,
    private val userRepository: UserRepository
) : DeviceService {

    override suspend fun findAll(): List<Device> =
        deviceRepository.findAll()

    override suspend fun findById(id: Int): Result<Device, DeviceError> =
        deviceRepository.findById(id)?.let {
            Ok(it)
        } ?: Err(DeviceError.NotFound("Device not found"))

    override suspend fun findAllByUserId(id: Int): List<Device> =
        deviceRepository.findAllByUserId(id)

    override suspend fun save(device: Device): Result<Device, DeviceError> =
        findById(device.id)
            .onSuccess {
                return Err(DeviceError.BadRequest("Device with this id already exists"))
            }
            .onFailure {
                deviceRepository.save(device)?.let {
                    return Ok(it)
                } ?: return@onFailure
            }

    override suspend fun update(device: Device): Result<Device, DeviceError> =
        findById(device.id)
            .onSuccess {
                deviceRepository.update(device)?.let {
                    return Ok(it)
                } ?: Err(DeviceError.BadRequest("Something went wrong..."))
            }
            .onFailure {
                return Err(DeviceError.BadRequest("Device doesn't exist"))
            }

    override suspend fun delete(id: Int): Result<Int, DeviceError> {
        findById(id)
            .onSuccess {
                deviceRepository.delete(it.id)?.let {
                    return Ok(it)
                } ?: run {
                    return Err(DeviceError.NotFound("Device not found"))
                }
            }
            .onFailure {
                return Err(DeviceError.NotFound("Device not found"))
            }
        return Err(DeviceError.NotFound("Device not found"))
    }

    override suspend fun mapDeviceToUser(userId: Int, deviceId: Int): Result<Unit, DeviceError> {
        userRepository.findById(userId)?.let {
            deviceRepository.findById(deviceId)?.let { device ->
                deviceRepository.update(device.copy(userId = userId))?.let {
                    return Ok(Unit)
                } ?: return Err(DeviceError.NotFound("Device cannot be mapped"))
            } ?: return Err(DeviceError.NotFound("Device doesn't exists!"))
        } ?: return Err(DeviceError.NotFound("User doesn't exists!"))
    }
}