package device.example

import device.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureKoin()
    configureDatabase()
    configureCors()
    configureSerialization()
    configureRouting()
}
