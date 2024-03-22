package com.example.plugins

import com.example.config.ServerConfig
import com.example.services.TokenService
import io.github.omkartenkale.ktor_role_based_auth.UnauthorizedAccessException
import io.github.omkartenkale.ktor_role_based_auth.roleBased
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject

private const val PERMISSION_DENIED = "You don't have enough permissions to access this route"
private const val USERNAME_CLAIM = "username"
private const val ROLE_CLAIM = "role"

fun Application.configureJWT() {

    val tokenService: TokenService by inject()
    val serverConfig: ServerConfig by inject()

    authentication {
        jwt {
            verifier(tokenService.verifyToken())
            realm = serverConfig.realm
            validate {
                if (it.payload.audience.contains(serverConfig.audience)
                    && it.payload.getClaim(USERNAME_CLAIM).asString().isNotEmpty()
                    && it.payload.getClaim(ROLE_CLAIM).asString().isNotEmpty()
                )
                    JWTPrincipal(it.payload)
                else
                    null
            }
            challenge { _, _ ->
                call.request.headers["Authorization"]?.let {
                    if (it.isNotEmpty()) {
                        call.respond(HttpStatusCode.Unauthorized)
                    } else {
                        throw BadRequestException("Authorization header can not be blank!")
                    }
                } ?: throw BadRequestException("Authorization header can not be blank!")
            }
        }
        roleBased {
            extractRoles { principal ->
                return@extractRoles setOf<String>(
                    (principal as JWTPrincipal)
                        .payload.claims
                        ?.get(ROLE_CLAIM)
                        .toString().replace("\"", "")
                )
            }
            throwErrorOnUnauthorizedResponse = true
        }
    }
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            if (cause is UnauthorizedAccessException) {
                call.respondText(
                    text = PERMISSION_DENIED,
                    status = HttpStatusCode.Forbidden
                )
            }
        }
    }
}