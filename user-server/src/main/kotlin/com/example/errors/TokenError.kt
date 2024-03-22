package com.example.errors

sealed class TokenError(val message: String) {
    class NotFound(message: String) : TokenError(message)
    class BadRequest(message: String) : TokenError(message)
    class BadCredentials(message: String) : TokenError(message)
    class BadRole(message: String) : TokenError(message)
    class Unauthorized(message: String) : TokenError(message)
    class Forbidden(message: String) : TokenError(message)
}