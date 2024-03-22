package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int = 0,
    val role: Role,
    val name: String,
    val username: String,
    val password: String
)
