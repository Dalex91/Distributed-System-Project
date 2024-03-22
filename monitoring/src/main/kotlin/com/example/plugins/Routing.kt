package com.example.plugins

import com.example.routing.monitoringRoutes
import io.ktor.server.application.*

fun Application.configureRouting() {
    monitoringRoutes()
}