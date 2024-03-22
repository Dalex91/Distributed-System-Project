package com.example.model

data class ChatMessage(
    val timestamp: Long,
    val sender: String,
    val text: String,
    var seen: Boolean = false
)