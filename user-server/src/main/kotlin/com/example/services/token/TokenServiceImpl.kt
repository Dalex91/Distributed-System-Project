package com.example.services.token

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.example.configuration.ServerConfig
import com.example.models.Tokens
import com.example.models.User
import org.koin.core.annotation.Singleton
import java.util.*

sealed class TokenException(message: String) : RuntimeException(message) {
    class InvalidTokenException(message: String) : TokenException(message)
}

private const val SUBJECT = "Authentication"
private const val SECONDS_TO_MILLIS = 1000L

@Singleton
class TokenServiceImpl(
    private val serverConfig: ServerConfig
) : TokenService {

    private val audience by lazy {
        serverConfig.audience
    }
    private val issuer by lazy {
        serverConfig.issuer
    }
    private val accessTokenExpiresIn by lazy {
        serverConfig.accessTokenExpiresIn
    }
    private val refreshTokenExpiresIn by lazy {
        serverConfig.refreshTokenExpiresIn
    }
    private val accessTokenSecret by lazy {
        serverConfig.accessTokenSecret
    }
    private val refreshTokenSecret by lazy {
        serverConfig.refreshTokenSecret
    }

    override fun generateAccessToken(user: User): String =
        JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withSubject(SUBJECT)
            .withClaim("username", user.name)
            .withClaim("role", user.role.toString())
            .withExpiresAt(Date(System.currentTimeMillis() + accessTokenExpiresIn * SECONDS_TO_MILLIS))
            .sign(
                Algorithm.HMAC512(accessTokenSecret)
            )

    override fun generateRefreshToken(user: User): String =
        JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", user.name)
            .withClaim("role", user.role.toString())
            .withExpiresAt(Date(System.currentTimeMillis() + refreshTokenExpiresIn * SECONDS_TO_MILLIS))
            .sign(
                Algorithm.HMAC512(refreshTokenSecret)
            )

    override fun generateTokens(user: User): Tokens =
        Tokens(
            accessToken = generateAccessToken(user),
            refreshToken = generateRefreshToken(user)
        )

    override fun verifyToken(): JWTVerifier =
        try {
            JWT.require(Algorithm.HMAC512(accessTokenSecret))
                .withAudience(audience)
                .withIssuer(issuer)
                .build()
        } catch (e: Exception) {
            throw TokenException.InvalidTokenException("Invalid token")
        }
}