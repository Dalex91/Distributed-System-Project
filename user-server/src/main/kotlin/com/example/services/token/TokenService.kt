package com.example.services.token

import com.auth0.jwt.JWTVerifier
import com.example.models.Tokens
import com.example.models.User

interface TokenService {

    fun generateAccessToken(user: User): String

    fun generateRefreshToken(user: User): String

    fun generateTokens(user: User): Tokens

    fun verifyToken(): JWTVerifier
}