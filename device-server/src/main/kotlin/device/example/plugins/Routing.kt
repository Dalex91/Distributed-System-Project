package device.example.plugins

import device.example.routing.deviceRoutes
import device.example.routing.userRoutes
import io.ktor.server.application.*

fun Application.configureRouting() {
    deviceRoutes()
    userRoutes()
}