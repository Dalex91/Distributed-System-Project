package com.example.repositories.activeUsers

import com.example.entities.ActiveUserEntity
import com.example.mappers.toActiveUserModel
import com.example.models.ActiveUser
import com.example.repositories.database.DatabaseFactory.dbQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ActiveUsersRepoImpl : ActiveUsersRepo {

    override suspend fun add(activeUser: ActiveUser): ActiveUser? = dbQuery {
        ActiveUserEntity.insert {
            activeUser.apply {
                it[ActiveUserEntity.userId] = activeUser.userId
                it[ActiveUserEntity.type] = activeUser.type
                it[ActiveUserEntity.token] = activeUser.token
                it[ActiveUserEntity.revoked] = activeUser.revoked
                it[ActiveUserEntity.createdAt] = activeUser.createdAt
            }
        }.resultedValues?.singleOrNull()?.toActiveUserModel()
    }

    override suspend fun delete(activeUser: ActiveUser): ActiveUser? = dbQuery {
        if (ActiveUserEntity.deleteWhere { userId eq activeUser.userId } == 1)
            activeUser
        else
            null
    }

    override suspend fun update(activeUser: ActiveUser): ActiveUser? = dbQuery {
        if (ActiveUserEntity.update({ ActiveUserEntity.id eq activeUser.id }) {
                activeUser.apply {
                    it[ActiveUserEntity.revoked] = activeUser.revoked
                }
            } == 1)
            activeUser.copy(revoked = activeUser.revoked)
        else
            null
    }

    override suspend fun findByUserId(userId: Int): Flow<ActiveUser> = dbQuery {
        flow {
            ActiveUserEntity
                .select(ActiveUserEntity.userId eq userId)
                .map { it.toActiveUserModel() }
        }
    }

    override suspend fun findTokenByUserId(userId: Int, token: String) = dbQuery {
        ActiveUserEntity
            .select((ActiveUserEntity.userId eq userId) and (ActiveUserEntity.token eq token))
            .map { it.toActiveUserModel() }
            .singleOrNull()
    }

    override suspend fun findById(id: Int): ActiveUser? = dbQuery {
        ActiveUserEntity
            .select(ActiveUserEntity.id eq id)
            .map { it.toActiveUserModel() }
            .singleOrNull()
    }

}