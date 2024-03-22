package device.example.services.user

import com.github.michaelbull.result.Result
import device.example.errors.DeviceError
import device.example.errors.UserError
import device.example.model.Device
import org.jetbrains.exposed.sql.ResultRow

interface UserService {

    suspend fun save(userId: Int): Result<Int, UserError>

    suspend fun delete(id: Int): Result<Int, UserError>

    suspend fun findById(userId: Int): Result<Int, UserError>
}