package device.example.configuration

import io.ktor.server.config.*
import org.koin.core.annotation.Singleton

@Singleton
class ServerConfig {
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
}