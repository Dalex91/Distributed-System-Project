package com.example.entities

import com.example.models.Role
import org.jetbrains.exposed.sql.Table

object UserEntity : Table("users") {
    val id = integer("id")
        .autoIncrement()
    val role = enumerationByName("role", 10, Role::class)
    val username = text("username")
    val name = text("name")
    val password = text("password")
    override val primaryKey = PrimaryKey(id)
}