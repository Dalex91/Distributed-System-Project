package com.example.services

import com.auth0.jwt.JWTVerifier

interface TokenService {
    fun verifyToken(): JWTVerifier
}