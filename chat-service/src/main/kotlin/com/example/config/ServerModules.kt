package com.example.config

import com.example.services.TokenService
import com.example.services.TokenServiceImpl
import org.koin.dsl.module

object ServerModules {

    private val serverConfigModule = module {
        single { ServerConfig() }
    }

    private val serviceModule = module {
        single<TokenService> { TokenServiceImpl(get()) }
    }

    private val repoModule = module {
    }

    val modules = listOf(
        serverConfigModule,
        serviceModule,
        repoModule
    )
}