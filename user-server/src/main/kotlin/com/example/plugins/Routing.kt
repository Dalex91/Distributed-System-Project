package com.example.plugins

import com.example.routing.userRoutes
import io.ktor.server.application.*

fun Application.configureRouting() {
    userRoutes()
}
