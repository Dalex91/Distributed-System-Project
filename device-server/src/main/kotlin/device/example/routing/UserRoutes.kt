package device.example.routing

import com.github.michaelbull.result.mapBoth
import device.example.errors.UserError
import device.example.mappers.toDto
import device.example.services.user.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import org.koin.ktor.ext.inject

private const val ENDPOINT = "/api/v1/user"

fun Application.userRoutes() {

    val deviceService: UserService by inject()

    routing {
        route(ENDPOINT) {

            post("/create") {
                call.parameters["userId"]?.toLong()?.let { id ->
                    deviceService.save(id.toInt())
                        .mapBoth(
                            success = { call.respond(HttpStatusCode.OK) },
                            failure = { handleUserError(it) }
                        )
                }
            }

            delete("/delete") {
                call.parameters["userId"]?.toLong()?.let { id ->
                    deviceService.delete(id.toInt())
                        .mapBoth(
                            success = { call.respond(HttpStatusCode.OK) },
                            failure = { handleUserError(it) }
                        )
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