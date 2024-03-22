package com.example

import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureKoin()
    configureDatabase()
    configureCors()
    configureSerialization()
    configureWebsockets()
    configureRouting()
    configureConsumer()
}
