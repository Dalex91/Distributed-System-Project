package com.example.configuration

import com.example.repositories.activeUsers.ActiveUsersRepo
import com.example.repositories.activeUsers.ActiveUsersRepoImpl
import com.example.repositories.user.UserRepository
import com.example.repositories.user.UserRepositoryImpl
import com.example.services.service.ActiveUsersService
import com.example.services.service.ActiveUsersServiceImpl
import com.example.services.token.TokenService
import com.example.services.token.TokenServiceImpl
import com.example.services.user.UserService
import com.example.services.user.UserServiceImpl
import org.koin.dsl.module

object ServerModules {

    private val serverConfigModule = module {
        single { ServerConfig() }
    }

    private val serviceModule = module {
        single<UserService> { UserServiceImpl(get()) }
        single<TokenService> { TokenServiceImpl(get()) }
        single<ActiveUsersService> { ActiveUsersServiceImpl(get()) }
    }

    private val repoModule = module {
        single<UserRepository> { UserRepositoryImpl() }
        single<ActiveUsersRepo> { ActiveUsersRepoImpl() }
    }

    val modules = listOf(
        serverConfigModule,
        serviceModule,
        repoModule
    )
}