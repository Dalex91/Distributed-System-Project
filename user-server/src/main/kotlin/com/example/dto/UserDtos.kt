package com.example.dto

import com.example.models.Role
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int,
    val role: Role,
    val username: String,
    val name: String,
    val password: String,
)

@Serializable
data class CreateUserDto(
    val username: String,
    val name: String,
    val password: String,
    val role: Role
)

@Serializable
data class UpdateUserDto(
    val username: String,
    val password: String,
    val name: String,
    val role: Role
)

@Serializable
data class UserLoginDto(
    val username: String,
    val password: String
)

@Serializable
data class UserLogoutDto(
    val token: String,
    val userId: Int
)

@Serializable
data class UserWithTokenDto(
    val user: UserDto,
    val token: String
)

@Serializable
data class UserLoginResponseDto(
    val id: Int,
    val username: String,
    val name: String,
    val role: Role,
    val tokens: TokensDto
)

@Serializable
data class TokensDto(
    val accessToken: String,
    val refreshToken: String
)

@Serializable
data class UserWithAccessToken(
    val username: String,
    val accessToken: String
)

@Serializable
data class UserWithDevice(
    val deviceId: Int,
    val username: String
)
