package com.example.routing

import ch.qos.logback.core.net.server.Client
import com.example.client.ClientConfig
import com.example.dto.CreateUserDto
import com.example.dto.UpdateUserDto
import com.example.dto.UserLoginDto
import com.example.dto.UserLogoutDto
import com.example.errors.TokenError
import com.example.errors.UserError
import com.example.mappers.toDto
import com.example.mappers.toLoginResponse
import com.example.mappers.toUserModel
import com.example.models.ActiveUser
import com.example.models.Role
import com.example.models.TokenType
import com.example.models.User
import com.example.services.service.ActiveUsersService
import com.example.services.token.TokenService
import com.example.services.user.UserService
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import io.github.omkartenkale.ktor_role_based_auth.withAnyRole
import io.github.omkartenkale.ktor_role_based_auth.withRole
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.koin.ktor.ext.inject
import java.time.LocalDateTime

private const val ENDPOINT = "api/v1"

fun Application.userRoutes() {

    val tokenService: TokenService by inject()
    val userService: UserService by inject()
    val activeUsersService: ActiveUsersService by inject()

    val client by lazy {
        HttpClient()
    }

    routing {

        route(ENDPOINT) {

            get("/init") {
                userService.save(
                    User(
                        username = "aladinul",
                        password = "1234",
                        role = Role.ADMIN,
                        name = "Alibaba"
                    )
                ).mapBoth(
                    success = { call.respond(HttpStatusCode.OK, "created") },
                    failure = { call.respond(HttpStatusCode.BadRequest) }
                )
            }

            post("/auth/login") {
                val userDto = call.receive<UserLoginDto>()
                userService.checkUserNameAndPassword(userDto.username, userDto.password)
                    .mapBoth(
                        success = { user: User ->
                            val tokens = tokenService.generateTokens(user)
                            activeUsersService.saveActiveUser(
                                ActiveUser(
                                    userId = user.id,
                                    token = tokens.accessToken,
                                    createdAt = LocalDateTime.now(),
                                    type = TokenType.ACCESS
                                )
                            )
                            call.respond(
                                HttpStatusCode.OK,
                                user.toLoginResponse(
                                    tokens.toDto()
                                )
                            )
                        }, failure = {
                            handleUserError(it)
                        }
                    )
            }

            get("/auth/logout") {
                val user = call.receive<UserLogoutDto>()
                activeUsersService.revokeToken(user.userId, user.token)
                    .onSuccess {
                        call.respond(
                            HttpStatusCode.OK,
                            mapOf("message" to "all good!")
                        )
                    }
                    .onFailure {
                        handleTokenErrors(it)
                    }
            }

            post("auth/refreshToken") {
                call.respond(
                    mapOf("refreshToken" to "TODO")
                )
            }

            authenticate {
                withAnyRole("ADMIN", "USER") {
                    get("/user/all") {
                        userService.findAll()
                            .map { it.toDto() }
                            .run {
                                call.respond(HttpStatusCode.OK, this)
                            }
                    }
                }

                withRole("ADMIN") {
                    delete("/user/delete") {
                        call.parameters["username"]?.let {
                            userService.delete(it)
                        }?.mapBoth(
                            success = {
                                call.respond(HttpStatusCode.NoContent)
                                client.delete(ClientConfig.appendEndpoint("/user/delete")) {
                                    contentType(
                                        ContentType.Application.Json
                                    )
                                    parameter("userId", it.id)
                                }
                            },
                            failure = { handleUserError(it) }
                        )
                    }

                    get("/user/get") {
                        call.parameters["username"]?.let {
                            userService.findByUsername(it)
                        }?.mapBoth(
                            success = {
                                call.respond(HttpStatusCode.OK, it)
                            },
                            failure = { handleUserError(it) }
                        )
                    }

                    post("/user/create") {
                        val userCreateDto = call.receive<CreateUserDto>()
                        userService.save(userCreateDto.toUserModel())
                            .mapBoth(
                                success = {
                                    call.respond(HttpStatusCode.Created, it)
                                    val response = client.post(ClientConfig.appendEndpoint("/user/create")) {
                                        contentType(ContentType.Application.Json)
                                        parameter("userId", it.id)
                                    }

                                    if (response.status == HttpStatusCode.OK) {
                                        println("STATUS ${response.status}")
                                        call.respond(
                                            HttpStatusCode.OK,
                                            "User created successfully. Client response status code: $response.status"
                                        )
                                    } else {
                                        println("STATUS ${response.status}")
                                        call.respond(
                                            HttpStatusCode.InternalServerError,
                                            "Error during client request. Status code: $response.status"
                                        )
                                    }
                                },
                                failure = { handleUserError(it) }
                            )
                    }

                    patch("/user/update") {
                        val userUpdateDto = call.receive<UpdateUserDto>()
                        userService.update(userUpdateDto.toUserModel())
                            .mapBoth(
                                success = {
                                    call.respond(HttpStatusCode.OK, it)
                                },
                                failure = { handleUserError(it) }
                            )
                    }
                }
            }
        }
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.handleUserError(
    error: Any
) {
    when (error) {
        is UserError.BadRequest -> call.respond(HttpStatusCode.BadRequest, mapOf("errorMessage" to error.message))
        is UserError.NotFound -> call.respond(HttpStatusCode.NotFound, mapOf("errorMessage" to error.message))
        is UserError.Unauthorized -> call.respond(HttpStatusCode.Unauthorized, mapOf("errorMessage" to error.message))
        is UserError.Forbidden -> call.respond(HttpStatusCode.Forbidden, mapOf("errorMessage" to error.message))
        is UserError.BadCredentials -> call.respond(HttpStatusCode.Unauthorized, mapOf("errorMessage" to error.message))
        is UserError.BadRole -> call.respond(HttpStatusCode.Forbidden, mapOf("errorMessage" to error.message))
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.handleTokenErrors(
    error: Any
) {
    when (error) {
        is TokenError.BadRequest -> call.respond(HttpStatusCode.BadRequest, mapOf("errorMessage" to error.message))
        is TokenError.NotFound -> call.respond(HttpStatusCode.NotFound, mapOf("errorMessage" to error.message))
        is TokenError.Unauthorized -> call.respond(HttpStatusCode.Unauthorized, mapOf("errorMessage" to error.message))
        is TokenError.Forbidden -> call.respond(HttpStatusCode.Forbidden, mapOf("errorMessage" to error.message))
        is TokenError.BadCredentials -> call.respond(
            HttpStatusCode.Unauthorized,
            mapOf("errorMessage" to error.message)
        )

        is TokenError.BadRole -> call.respond(HttpStatusCode.Forbidden, mapOf("errorMessage" to error.message))
    }
}