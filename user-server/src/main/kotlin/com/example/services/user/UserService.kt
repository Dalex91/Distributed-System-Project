package com.example.services.user

import com.github.michaelbull.result.Result
import com.example.errors.UserError
import com.example.models.User
import kotlinx.coroutines.flow.Flow

interface UserService {

    suspend fun findAll(): List<User>

    suspend fun findById(id: Int): Result<User, UserError>

    suspend fun findByUsername(username: String): Result<User, UserError>

    suspend fun checkUserNameAndPassword(username: String, password: String): Result<User, UserError>

    suspend fun save(user: User): Result<User, UserError>

    suspend fun update(user: User): Result<User, UserError>

    suspend fun delete(username: String): Result<User, UserError>

    suspend fun isAdmin(username: String): Result<Boolean, UserError>

}