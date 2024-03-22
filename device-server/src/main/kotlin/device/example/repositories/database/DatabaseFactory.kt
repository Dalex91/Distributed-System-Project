package device.example.repositories.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import device.example.configuration.ServerConfig
import device.example.entities.DeviceEntity
import device.example.entities.UserEntity
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object DatabaseFactory : KoinComponent {

    private val serverConfig: ServerConfig by inject()
    private val connectionPool by lazy {
        createHikariDataSource(
            url = "${serverConfig.jdbcURL}/${serverConfig.postgresHost}:${serverConfig.postgresPort}/${serverConfig.defaultDatabase}?user=${serverConfig.username}&password=${serverConfig.password}",
            driver = serverConfig.driverClassName,
            serverConfig.maxPoolSize.toInt(),
            serverConfig.autoCommit.toBoolean()
        )
    }

    private val database by lazy {
        Database.connect(connectionPool)
    }

    fun init() {
        transaction(database) {
            SchemaUtils.create(UserEntity)
            SchemaUtils.create(DeviceEntity)
        }
    }

    private fun createHikariDataSource(
        url: String,
        driver: String,
        maxPoolSize: Int,
        autoCommit: Boolean
    ) = HikariDataSource(HikariConfig().apply {
        driverClassName = driver
        jdbcUrl = url
        maximumPoolSize = maxPoolSize
        isAutoCommit = autoCommit
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    })

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}