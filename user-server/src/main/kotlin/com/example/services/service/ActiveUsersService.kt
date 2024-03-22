package com.example.services.service

import com.github.michaelbull.result.Result
import com.example.errors.TokenError
import com.example.models.ActiveUser
import com.example.models.TokenType

interface ActiveUsersService {

    suspend fun saveActiveUser(activeUser: ActiveUser): Result<ActiveUser, TokenError>

    suspend fun findActiveUserToken(userId: Int): Result<ActiveUser, TokenError>

    suspend fun revokeToken(userId: Int, token: String): Result<ActiveUser, TokenError>

}