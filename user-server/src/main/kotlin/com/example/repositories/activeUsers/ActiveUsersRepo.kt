package com.example.repositories.activeUsers

import com.example.models.ActiveUser
import kotlinx.coroutines.flow.Flow

interface ActiveUsersRepo {

    suspend fun add(activeUser: ActiveUser): ActiveUser?

    suspend fun delete(activeUser: ActiveUser): ActiveUser?

    suspend fun update(activeUser: ActiveUser): ActiveUser?

    suspend fun findByUserId(userId: Int): Flow<ActiveUser?>

    suspend fun findById(id: Int): ActiveUser?

    suspend fun findTokenByUserId(userId: Int, token: String): ActiveUser?

}