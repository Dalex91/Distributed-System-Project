package com.example.repository

import com.example.model.ChatSession

class ChatRepository {
    private val sessions = mutableMapOf<String, ChatSession>()

    fun getSession(sessionId: String): ChatSession? {
        return sessions[sessionId]
    }

    fun createSession(sender: String, receiver: String): String {
        val sessionId = "${sender}_$receiver"
        sessions[sessionId] = ChatSession(sender, receiver, sessionId)
        return sessionId
    }
}