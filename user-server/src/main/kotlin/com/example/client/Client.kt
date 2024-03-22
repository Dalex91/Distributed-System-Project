package com.example.client

import com.example.configuration.ServerConfig
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object ClientConfig : KoinComponent {

    private val serverConfig: ServerConfig by inject()

    private val baseUrl by lazy {
        "http://${serverConfig.baseUrl}:8080/api/v1"
    }

    fun appendEndpoint(endpoint: String) =
        baseUrl.plus(endpoint)
}