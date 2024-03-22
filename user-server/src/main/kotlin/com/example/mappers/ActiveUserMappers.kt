package com.example.mappers

import com.example.entities.ActiveUserEntity
import com.example.models.ActiveUser
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toActiveUserModel(): ActiveUser =
    ActiveUser(
        id = this[ActiveUserEntity.id],
        userId = this[ActiveUserEntity.userId],
        token = this[ActiveUserEntity.token],
        revoked = this[ActiveUserEntity.revoked],
        createdAt = this[ActiveUserEntity.createdAt],
        type = this[ActiveUserEntity.type]
    )