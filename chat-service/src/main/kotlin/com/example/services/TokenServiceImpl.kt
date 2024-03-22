package com.example.services

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.example.config.ServerConfig
import org.koin.core.annotation.Singleton


sealed class TokenException(message: String) : RuntimeException(message) {
    class InvalidTokenException(message: String) : TokenException(message)
}

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
    private val accessTokenSecret by lazy {
        serverConfig.accessTokenSecret
    }

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