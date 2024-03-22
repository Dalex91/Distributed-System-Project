package com.example.configuration

import io.ktor.server.config.*
import org.koin.core.annotation.Singleton

@Singleton
class ServerConfig {
    private val config = ApplicationConfig("application.conf")

    // Database
    val driverClassName = config.property("ktor.database.driverClassName").getString()
    val jdbcURL = config.property("ktor.database.jdbcURL").getString()
    val maxPoolSize = config.property("ktor.database.maxPoolSize").getString()
    val autoCommit = config.property("ktor.database.autoCommit").getString()
    val username = config.property("ktor.database.user").getString()
    val password = config.property("ktor.database.password").getString()
    val defaultDatabase = config.property("ktor.database.database").getString()
    val postgresPort = config.property("ktor.database.postgresPort").getString()
    val postgresHost = config.property("ktor.database.postgresHost").getString()

    // JWT
    val audience = config.property("ktor.jwt.audience").getString()
    val realm = config.property("ktor.jwt.realm").getString()
    val issuer = config.property("ktor.jwt.issuer").getString()
    val accessTokenExpiresIn = config.property("ktor.jwt.expirationAccessToken").getString().toLong()
    val refreshTokenExpiresIn = config.property("ktor.jwt.expirationRefreshToken").getString().toLong()
    val accessTokenSecret = config.property("ktor.jwt.accessTokenSecret").getString()
    val refreshTokenSecret = config.property("ktor.jwt.refreshTokenSecret").getString()

    // CLIENT
    val baseUrl = config.property("ktor.client.baseUrl").getString()
}