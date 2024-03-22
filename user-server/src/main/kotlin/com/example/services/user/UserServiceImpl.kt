package com.example.services.user

import com.example.errors.UserError
import com.example.models.Role
import com.example.models.User
import com.example.repositories.user.UserRepository
import com.github.michaelbull.result.*
import kotlinx.coroutines.flow.Flow

class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    override suspend fun findAll(): List<User> =
        userRepository.findAll()

    override suspend fun findById(id: Int): Result<User, UserError> =
        userRepository.findById(id)?.let {
            Ok(it)
        } ?: Err(UserError.NotFound("User not found!"))

    override suspend fun findByUsername(username: String): Result<User, UserError> =
        userRepository.findByUsername(username)?.let {
            Ok(it)
        } ?: Err(UserError.NotFound("User not found!"))

    override suspend fun checkUserNameAndPassword(username: String, password: String): Result<User, UserError> =
        userRepository.checkUserNameAndPassword(username, password)?.let {
            Ok(it)
        } ?: Err(UserError.BadCredentials("User password or username not valid"))

    override suspend fun save(user: User): Result<User, UserError> =
        findByUsername(user.username)
            .onSuccess {
                return Err(UserError.BadRequest("Another user has the same username"))
            }
            .onFailure {
                userRepository.save(user)?.let {
                    return Ok(it)
                } ?: return@onFailure
            }

    override suspend fun update(user: User): Result<User, UserError> =
        findByUsername(user.username)
            .onSuccess {
                userRepository.update(user).let {
                    return if (it)
                        Ok(user)
                    else
                        Err(UserError.NotFound("Something went wrong..."))
                }
            }
            .onFailure {
                return Err(UserError.NotFound("User doesn't exists"))
            }


    override suspend fun delete(username: String): Result<User, UserError> =
        findByUsername(username)
            .onSuccess {
                Ok(userRepository.delete(username))
            }
            .onFailure {
                Err(UserError.NotFound("User not found!"))
            }

    override suspend fun isAdmin(username: String): Result<Boolean, UserError> {
        findByUsername(username)
            .onSuccess {
                return Ok(it.role == Role.ADMIN)
            }
            .onFailure {
                return Err(UserError.NotFound("User not found!"))
            }
        return Err(UserError.BadRequest("Something went wrong"))
    }
}