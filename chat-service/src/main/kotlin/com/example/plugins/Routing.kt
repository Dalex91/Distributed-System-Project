package com.example.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.time.Duration
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

private const val ENDPOINT = "/api/v1/chat"

fun Application.configureRouting() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(1)
        timeout = Duration.ofHours(1)
        maxFrameSize = Long.MAX_VALUE
        masking = false

    }

    val connections = ConcurrentHashMap<String, DefaultWebSocketServerSession>()
    val messageHistory = ConcurrentHashMap<String, MutableList<String>>()
    val unseenMessages = ConcurrentHashMap<String, Boolean>()
    val activeConversations = ConcurrentHashMap<String, String>()

    routing {
        route(ENDPOINT) {

            webSocket("/{username}") {
                val username = call.parameters["username"] ?: return@webSocket close(
                    CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No username provided")
                )
                connections[username] = this

                try {
                    for (frame in incoming) {
                        frame as? Frame.Text ?: continue
                        val receivedText = frame.readText()

                        if (receivedText.startsWith("openChat:")) {
                            val targetUsername = receivedText.removePrefix("openChat:")
                            val chatKey = listOf(username, targetUsername).sorted().joinToString(":")
                            activeConversations[username] = chatKey
                            unseenMessages[chatKey] = false

                            connections[targetUsername]?.send(Frame.Text("seen:$username"))
                        } else if (!receivedText.startsWith("typing:")) {
                            val (targetUsername, message) = receivedText.split(":", limit = 2)
                            val timestamp = Instant.now().toEpochMilli()
                            val chatKey = listOf(username, targetUsername).sorted().joinToString(":")

                            messageHistory.computeIfAbsent(chatKey) { mutableListOf() }
                                .add("$timestamp:$username: $message")

                            connections[targetUsername]?.send(Frame.Text("$username: $message"))

                            if (activeConversations[targetUsername] == chatKey) {
                                unseenMessages[chatKey] = false
                                this.send(Frame.Text("seen:$targetUsername"))
                            } else {
                                unseenMessages[chatKey] = true
                            }
                        } else {
                            val targetUsername = receivedText.removePrefix("typing:")
                            connections[targetUsername]?.send(Frame.Text("$username is typing..."))
                        }
                    }
                } catch (e: Exception) {
                    println("Error in websocket: ${e.localizedMessage}")
                } finally {
                    connections.remove(username)
                    activeConversations.remove(username)
                }
            }


            get("/messages/{username}/{targetUsername}") {
                val username = call.parameters["username"] ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    "Missing username"
                )
                val targetUsername = call.parameters["targetUsername"] ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    "Missing target username"
                )

                val chatKeySenderToReceiver = "$username:$targetUsername"
                val chatKeyReceiverToSender = "$targetUsername:$username"

                val messagesSenderToReceiver = messageHistory[chatKeySenderToReceiver] ?: listOf()
                val messagesReceiverToSender = messageHistory[chatKeyReceiverToSender] ?: listOf()

                val combinedMessages = (messagesSenderToReceiver + messagesReceiverToSender)
                    .map { msg ->
                        val (timestamp, sender, text) = msg.split(":", limit = 3)
                        Triple(timestamp.toLong(), sender, text)
                    }
                    .sortedBy { it.first }

                val formattedMessages = combinedMessages.map { (timestamp, sender, text) ->
                    val senderLabel = if (sender == username) "Me" else sender
                    val isoTimestamp = Instant.ofEpochMilli(timestamp).toString()
                    "$isoTimestamp: $senderLabel: $text"
                }

                val chatKey = listOf(username, targetUsername).sorted().joinToString(":")
                unseenMessages[chatKey] = false

                call.respond(HttpStatusCode.OK, formattedMessages)
            }
        }
    }
}
