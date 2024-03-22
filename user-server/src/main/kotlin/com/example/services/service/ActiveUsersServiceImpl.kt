package com.example.services.service

import com.example.errors.TokenError
import com.example.models.ActiveUser
import com.example.repositories.activeUsers.ActiveUsersRepo
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.singleOrNull

class ActiveUsersServiceImpl(
    private val activeUsersRepo: ActiveUsersRepo
) : ActiveUsersService {

    override suspend fun saveActiveUser(activeUser: ActiveUser): Result<ActiveUser, TokenError> =
        activeUsersRepo.add(activeUser)?.let {
            Ok(it)
        } ?: Err(TokenError.BadRequest("Bad request"))

    override suspend fun findActiveUserToken(userId: Int): Result<ActiveUser, TokenError> =
        activeUsersRepo.findByUserId(userId).filter {
            it?.revoked ?: false
        }.singleOrNull()?.let {
            Ok(it)
        } ?: Err(TokenError.NotFound("Token not found"))

    override suspend fun revokeToken(userId: Int, token: String): Result<ActiveUser, TokenError> =
        activeUsersRepo.findTokenByUserId(userId, token)?.let { activeUser ->
            activeUsersRepo.update(
                activeUser = activeUser.copy(revoked = true)
            )?.let {
                Ok(it)
            } ?: Err(TokenError.NotFound("Token not found"))
        } ?: Err(TokenError.NotFound("Token not found"))
}