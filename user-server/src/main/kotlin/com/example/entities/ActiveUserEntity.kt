package com.example.entities

import com.example.models.TokenType
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object ActiveUserEntity : Table("active_users") {
    val id = integer("id").autoIncrement()
    val userId = reference(
        "user_id",
        UserEntity.id,
        onDelete = ReferenceOption.CASCADE
    )
    val token = text("token")
    val revoked = bool("revoked")
    val createdAt = datetime("created_at")
    val type = enumerationByName("type", 10, TokenType::class)
    override val primaryKey = PrimaryKey(id)
}