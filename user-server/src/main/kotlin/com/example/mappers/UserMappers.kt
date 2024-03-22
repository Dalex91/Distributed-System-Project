package com.example.mappers

import com.example.dto.*
import com.example.entities.UserEntity
import com.example.models.Role
import com.example.models.Tokens
import com.example.models.User
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow

fun User.toDto(): UserDto =
    UserDto(
        id = this.id,
        role = this.role,
        name = this.name,
        password = this.password,
        username = this.username
    )

fun CreateUserDto.toUserModel(): User =
    User(
        username = this.username,
        name = this.name,
        password = this.password,
        role = this.role
    )

fun UpdateUserDto.toUserModel(): User =
    User(
        username = this.username,
        name = this.name,
        role = this.role,
        password = this.password
    )

fun User.toLoginResponse(tokens: TokensDto): UserLoginResponseDto =
    UserLoginResponseDto(
        id = this.id,
        username = this.username,
        role = this.role,
        name = this.name,
        tokens = tokens
    )


fun ResultRow.toUserModel(): User =
    User(
        id = this[UserEntity.id],
        name = this[UserEntity.name],
        username = this[UserEntity.username],
        password = this[UserEntity.password],
        role = this[UserEntity.role],
    )

fun TokensDto.toUserModel(): Tokens =
    Tokens(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken
    )

fun Tokens.toDto(): TokensDto =
    TokensDto(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken
    )
