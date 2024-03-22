package com.example.config

import io.ktor.server.config.*
import org.koin.core.annotation.Singleton


@Singleton
class ServerConfig {
    private val config = ApplicationConfig("application.conf")

    // JWT
    val audience = config.property("ktor.jwt.audience").getString()
    val realm = config.property("ktor.jwt.realm").getString()
    val issuer = config.property("ktor.jwt.issuer").getString()
    val accessTokenSecret = config.property("ktor.jwt.accessTokenSecret").getString()
}