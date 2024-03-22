package device.example.routing

import com.github.michaelbull.result.mapBoth
import device.example.dto.CreateDeviceDto
import device.example.dto.DeviceAndUserIdsDto
import device.example.dto.UpdateDeviceDto
import device.example.errors.DeviceError
import device.example.mappers.toDto
import device.example.mappers.toDeviceModel
import device.example.services.device.DeviceService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*

import org.koin.ktor.ext.inject

private const val ENDPOINT = "api/v1/device"

fun Application.deviceRoutes() {

    val deviceService: DeviceService by inject()

    routing {
        route(ENDPOINT) {
            get("/all") {
                deviceService.findAll()
                    .run {
                        call.respond(
                            HttpStatusCode.OK,
                            this
                        )
                    }
            }

            get("/my-devices") {
                call.parameters["userId"]?.let {
                    deviceService.findAllByUserId(it.toInt())
                        .map { device ->
                            device.toDto()
                        }
                        .run {
                            call.respond(
                                HttpStatusCode.OK,
                                this
                            )
                        }
                }
            }

            post("/create-device") {
                val createDevice = call.receive<CreateDeviceDto>()
                deviceService.save(createDevice.toDeviceModel())
                    .mapBoth(
                        success = { call.respond(HttpStatusCode.OK, it) },
                        failure = { handleDeviceError(it) }
                    )
            }

            get() {
                call.parameters["id"]?.let { id ->
                    deviceService.findById(id.toInt())
                        .mapBoth(
                            success = { call.respond(HttpStatusCode.OK, it.toDto()) },
                            failure = { handleDeviceError(it) }
                        )
                }
            }

            post("/map-device") {
                val deviceAndUserIdsDto = call.receive<DeviceAndUserIdsDto>()
                deviceService.mapDeviceToUser(
                    userId = deviceAndUserIdsDto.userId,
                    deviceId = deviceAndUserIdsDto.deviceId
                ).mapBoth(
                    success = { call.respond(HttpStatusCode.OK) },
                    failure = { handleDeviceError(it) }
                )
            }

            delete("/delete") {
                call.parameters["deviceId"]?.let { devId ->
                    deviceService.delete(devId.toInt())
                        .mapBoth(
                            success = { call.respond(HttpStatusCode.OK) },
                            failure = { handleDeviceError(it) }
                        )
                }
            }

            get("/") {
                call.parameters["deviceId"]?.let { devId ->
                    deviceService.findById(devId.toInt())
                        .mapBoth(
                            success = { call.respond(HttpStatusCode.OK, it) },
                            failure = { handleDeviceError(it) }
                        )
                }
            }

            patch("/update") {
                val updateDevice = call.receive<UpdateDeviceDto>()
                deviceService.update(updateDevice.toDeviceModel())
                    .mapBoth(
                        success = { call.respond(HttpStatusCode.OK) },
                        failure = { handleDeviceError(it) }
                    )
            }
        }
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.handleDeviceError(
    error: Any
) {
    when (error) {
        is DeviceError.BadRequest -> call.respond(HttpStatusCode.BadRequest, mapOf("errorMessage" to error.message))
        is DeviceError.NotFound -> call.respond(HttpStatusCode.NotFound, mapOf("errorMessage" to error.message))
        is DeviceError.Unauthorized -> call.respond(HttpStatusCode.Unauthorized, mapOf("errorMessage" to error.message))
        is DeviceError.Forbidden -> call.respond(HttpStatusCode.Forbidden, mapOf("errorMessage" to error.message))
        is DeviceError.BadCredentials -> call.respond(HttpStatusCode.BadRequest, mapOf("errorMessage" to error.message))
        is DeviceError.BadRole -> call.respond(HttpStatusCode.Forbidden, mapOf("errorMessage" to error.message))
    }
}