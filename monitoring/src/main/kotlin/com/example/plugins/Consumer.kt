package com.example.plugins

import com.example.consumer.config.ConsumerFactory
import io.ktor.server.application.*

fun Application.configureConsumer() {
    ConsumerFactory.initSensorConsumer()
    ConsumerFactory.initDeviceSyncConsumer()
}