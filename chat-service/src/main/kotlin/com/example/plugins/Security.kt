package com.example.plugins

import com.example.model.ChatSession
import io.ktor.server.application.*
import io.ktor.server.sessions.*

private const val SESSION_NAME = "SESSION"
private const val SENDER_NAME = "SENDER"
private const val RECEIVER_NAME = "RECEIVER"

fun Application.configureSecurity() {

    install(Sessions) {
        cookie<ChatSession>(SESSION_NAME)
    }
}