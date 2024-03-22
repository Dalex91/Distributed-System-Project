package com.example.consumer.config

import com.example.configuration.ServerConfig
import com.example.consumer.handler.ConsumerHandler
import com.example.dto.MeasurementDataDto
import com.example.mappers.toModel
import com.example.model.Device
import com.example.model.Operation
import com.google.gson.JsonParser
import com.rabbitmq.client.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlinx.serialization.decodeFromString

object ConsumerFactory : KoinComponent {

    private val consumerHandler: ConsumerHandler by inject()
    private val serverConfig: ServerConfig by inject()

    private val json = Json { encodeDefaults = true }

    private val connectionFactory by lazy {
        ConnectionFactory().apply {
            username = serverConfig.userRMQ
            password = serverConfig.passwordRMQ
            host = serverConfig.hostRMQ
            virtualHost = serverConfig.virtualHost
            port = serverConfig.portRMQ
        }
    }
    private val channel by lazy {
        connectionFactory.newConnection().createChannel()
    }

    fun initSensorConsumer() {
        channel.basicConsume(serverConfig.sensorValuesQueue, true, object : Consumer {

            override fun handleCancelOk(consumerTag: String?) = Unit

            override fun handleCancel(consumerTag: String?) = Unit

            override fun handleRecoverOk(consumerTag: String?) = Unit

            override fun handleConsumeOk(consumerTag: String?) {
                consumerTag?.let {
                    println("$it has been registered as a callback")
                }
            }

            override fun handleShutdownSignal(consumerTag: String?, sig: ShutdownSignalException?) {
                sig?.let {
                    throw it
                }
            }

            override fun handleDelivery(
                consumerTag: String?,
                envelope: Envelope?,
                properties: AMQP.BasicProperties?,
                body: ByteArray?
            ) {
                body?.let {
                    runBlocking {
                        val measurementDataDto = json.decodeFromString<MeasurementDataDto>(
                            body.decodeToString()
                        )
                        consumerHandler.saveMeasurement(measurementDataDto.toModel())
                        println("Received MeasurementData: $measurementDataDto")
                    }
                }
            }
        })
    }

    fun initDeviceSyncConsumer() {
        channel.basicConsume(serverConfig.deviceSyncQueue, true, object : Consumer {

            override fun handleCancelOk(consumerTag: String?) = Unit

            override fun handleCancel(consumerTag: String?) = Unit

            override fun handleRecoverOk(consumerTag: String?) = Unit

            override fun handleConsumeOk(consumerTag: String?) {
                consumerTag?.let {
                    println("$it has been registered as a callback")
                }
            }

            override fun handleShutdownSignal(consumerTag: String?, sig: ShutdownSignalException?) {
                sig?.let {
                    throw it
                }
            }

            override fun handleDelivery(
                consumerTag: String?,
                envelope: Envelope?,
                properties: AMQP.BasicProperties?,
                body: ByteArray?
            ) {
                body?.let {
                    val message = it.decodeToString()
                    val jsonElement = JsonParser.parseString(message)
                    val operation = Operation.valueOf(jsonElement.asJsonObject.get("operation").asString)
                    runBlocking {
                        when (operation) {
                            Operation.CREATE -> {
                                consumerHandler.saveDevice(
                                    Device(
                                        id = jsonElement.asJsonObject.get("device_id").asInt,
                                        maxHourlyEnergyConsumption = jsonElement.asJsonObject.get("max_hourly_energy_consumption").asDouble
                                    )
                                )
                            }

                            Operation.UPDATE -> {
                                consumerHandler.updateDevice(
                                    Device(
                                        id = jsonElement.asJsonObject.get("device_id").asInt,
                                        maxHourlyEnergyConsumption = jsonElement.asJsonObject.get("max_hourly_energy_consumption").asDouble
                                    )
                                )
                            }

                            Operation.DELETE -> {
                                consumerHandler.deleteDevice(
                                    id = jsonElement.asJsonObject.get("device_id").asInt
                                )
                            }
                        }
                    }
                }
            }
        })
    }
}