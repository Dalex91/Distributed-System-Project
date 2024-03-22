package com.example.configuration

import io.ktor.server.config.*
import org.koin.core.annotation.Singleton

@Singleton
class ServerConfig {

    // DATABASE
    private val config = ApplicationConfig("application.conf")
    val driverClassName = config.property("ktor.database.driverClassName").getString()
    val jdbcURL = config.property("ktor.database.jdbcURL").getString()
    val maxPoolSize = config.property("ktor.database.maxPoolSize").getString()
    val autoCommit = config.property("ktor.database.autoCommit").getString()
    val username = config.property("ktor.database.user").getString()
    val password = config.property("ktor.database.password").getString()
    val defaultDatabase = config.property("ktor.database.database").getString()
    val postgresPort = config.property("ktor.database.postgresPort").getString()
    val postgresHost = config.property("ktor.database.postgresHost").getString()

    // RABBIT MQ CREDENTIALS
    val userRMQ = config.property("ktor.rabbitmq.user").getString()
    val passwordRMQ = config.property("ktor.rabbitmq.password").getString()

    // RABBIT MQ CONNECTION PROPERTIES
    val hostRMQ = config.property("ktor.rabbitmq.hostRMQ").getString()
    val portRMQ = config.property("ktor.rabbitmq.portRMQ").getString().toInt()
    val virtualHost = config.property("ktor.rabbitmq.virtual_host").getString()

    // QUEUES
    val sensorValuesQueue = config.property("ktor.rabbitmq.simulation_queue").getString()
    val deviceSyncQueue = config.property("ktor.rabbitmq.device_sync_queue").getString()
}