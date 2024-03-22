package device.example.services.user

import com.github.michaelbull.result.*
import device.example.errors.UserError
import device.example.repositories.user.UserRepository

class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    override suspend fun save(userId: Int): Result<Int, UserError> =
        findById(userId)
            .onSuccess {
                return Err(UserError.BadRequest("User with the same id already exists!"))
            }
            .onFailure {
                userRepository.save(userId)?.let {
                    return Ok(it)
                } ?: return@onFailure
            }

    override suspend fun delete(id: Int): Result<Int, UserError> {
        findById(id)
            .onSuccess {
                userRepository.delete(id)?.let {
                    return Ok(it)
                } ?: run {
                    return Err(UserError.NotFound("Device not found"))
                }
            }
            .onFailure {
                return Err(UserError.NotFound("Device not found"))
            }
        return Err(UserError.NotFound("Device not found"))
    }

    override suspend fun findById(userId: Int): Result<Int, UserError> =
        userRepository.findById(userId)?.let {
            Ok(it)
        } ?: Err(UserError.NotFound("User not found"))
}