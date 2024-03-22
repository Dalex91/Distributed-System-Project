package com.example.routing

import com.example.dto.HourlyConsumePerDayDto
import com.example.services.monitoring.MonitoringManagementService
import com.example.util.parseDateStringToInstant
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.ktor.ext.inject

private const val ENDPOINT = "/api/v1/monitoring"

fun Application.monitoringRoutes() {

    val monitoringManagementService: MonitoringManagementService by inject()

    routing {
        route(ENDPOINT) {

            post("/hourly-consumption-per-day") {
                val hourlyConsumePerDay = call.receive<HourlyConsumePerDayDto>()
                monitoringManagementService.getConsumePerDay(
                    date = parseDateStringToInstant(hourlyConsumePerDay.date),
                    deviceId = hourlyConsumePerDay.deviceId
                ).run {
                    println(this)
                    call.respond(HttpStatusCode.OK, this)
                }
            }

            webSocket("/notification") {
                monitoringManagementService.notificationState.collect {
                    println(it.toString())
                    sendSerialized(it)
                }
            }
        }
    }
}