package device.example.configuration

import device.example.repositories.device.DeviceRepository
import device.example.repositories.device.DeviceRepositoryImpl
import device.example.repositories.user.UserRepository
import device.example.repositories.user.UserRepositoryImpl
import device.example.services.device.DeviceService
import device.example.services.device.DeviceServiceImpl
import device.example.services.user.UserService
import device.example.services.user.UserServiceImpl
import org.koin.dsl.module

object ServerModules {

    private val serverConfigModule = module {
        single { ServerConfig() }
    }

    private val serviceModule = module {
        single<DeviceService> { DeviceServiceImpl(get(), get()) }
        single<UserService> { UserServiceImpl(get()) }
    }

    private val repoModule = module {
        single<DeviceRepository> { DeviceRepositoryImpl() }
        single<UserRepository> { UserRepositoryImpl(get()) }
    }

    val modules = listOf(
        serverConfigModule,
        serviceModule,
        repoModule
    )
}
