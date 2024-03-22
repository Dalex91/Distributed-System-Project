package com.example.models

import java.time.LocalDateTime

data class ActiveUser(
    val id: Int = 0,
    val userId: Int,
    val token: String,
    val revoked: Boolean = false,
    val createdAt: LocalDateTime,
    val type: TokenType
)