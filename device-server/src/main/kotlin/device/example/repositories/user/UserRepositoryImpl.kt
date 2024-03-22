package device.example.repositories.user

import device.example.entities.UserEntity
import device.example.mappers.toUserModel
import device.example.repositories.database.DatabaseFactory.dbQuery
import device.example.repositories.device.DeviceRepository
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update

class UserRepositoryImpl(
    private val deviceRepository: DeviceRepository
) : UserRepository {
    override suspend fun save(userId: Int): Int? = dbQuery {
        UserEntity.insert {
            it[UserEntity.userID] = userId
        }.resultedValues?.singleOrNull()?.toUserModel()
    }

    override suspend fun delete(id: Int): Int? = dbQuery {
        if (UserEntity.deleteWhere { this.userID eq id } == 1) {
            deviceRepository.updateOnUserDelete(id)
            id
        } else
            null
    }

    override suspend fun findById(id: Int): Int? = dbQuery {
        UserEntity
            .select { UserEntity.userID eq id }.map { it.toUserModel() }
            .singleOrNull()
    }
}