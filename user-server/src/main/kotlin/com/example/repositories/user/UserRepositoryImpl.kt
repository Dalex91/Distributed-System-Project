package com.example.repositories.user

import com.example.entities.UserEntity
import com.example.mappers.toUserModel
import com.example.models.User
import com.example.repositories.database.DatabaseFactory.dbQuery
import com.toxicbakery.bcrypt.Bcrypt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class UserRepositoryImpl : UserRepository {

    override suspend fun save(entity: User): User? = dbQuery {
        UserEntity.insert {
            entity.apply {
                it[UserEntity.password] = Bcrypt.hash(password, 12).decodeToString()
                it[UserEntity.username] = username
                it[UserEntity.name] = name
                it[UserEntity.role] = role
            }
        }.resultedValues?.singleOrNull()?.toUserModel()
    }

    override suspend fun findAll(): List<User> = dbQuery {
        UserEntity.selectAll().map {
            it.toUserModel()
        }
    }

    override suspend fun findById(id: Int): User? = dbQuery {
        UserEntity
            .select(UserEntity.id eq id)
            .map { it.toUserModel() }
            .singleOrNull()
    }

    override suspend fun findByUsername(username: String): User? = dbQuery {
        UserEntity
            .select(UserEntity.username eq username)
            .map { it.toUserModel() }
            .singleOrNull()
    }

    override suspend fun update(entity: User): Boolean = dbQuery {
        UserEntity.update({ UserEntity.username eq entity.username }) {
            entity.apply {
                it[UserEntity.username] = username
                it[UserEntity.name] = name
                it[UserEntity.role] = role
            }
        } == 1
    }

    override suspend fun delete(entity: String): Boolean = dbQuery {
        UserEntity.deleteWhere {
            username eq entity
        } == 1
    }

    override suspend fun deleteAll() {
        UserEntity.deleteAll()
    }

    override suspend fun checkUserNameAndPassword(username: String, password: String): User? = dbQuery {
        findByUsername(username)?.let {
            when (Bcrypt.verify(password, it.password.encodeToByteArray())) {
                true -> it
                false -> null
            }
        }
    }
}