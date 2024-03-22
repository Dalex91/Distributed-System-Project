package com.example.configuration


import com.example.consumer.handler.ConsumerHandler
import com.example.consumer.handler.ConsumerHandlerImpl
import com.example.repositories.device.DeviceRepository
import com.example.repositories.device.DeviceRepositoryImpl
import com.example.repositories.monitoring.MonitoringManagementRepository
import com.example.repositories.monitoring.MonitoringManagementRepositoryImpl
import com.example.services.device.DeviceService
import com.example.services.device.DeviceServiceImpl
import com.example.services.monitoring.MonitoringManagementService
import com.example.services.monitoring.MonitoringManagementServiceImpl
import org.koin.dsl.module

object ServerModules {

    private val serverConfigModule = module {
        single { ServerConfig() }
    }

    private val serviceModule = module {
        single<MonitoringManagementService> { MonitoringManagementServiceImpl(get(), get()) }
        single<DeviceService> { DeviceServiceImpl(get()) }
    }

    private val repoModule = module {
        single<MonitoringManagementRepository> { MonitoringManagementRepositoryImpl() }
        single<DeviceRepository> { DeviceRepositoryImpl(get()) }
    }

    private val consumerModule = module {
        single<ConsumerHandler> { ConsumerHandlerImpl(get(), get()) }
    }

    val modules = listOf(
        serverConfigModule,
        serviceModule,
        consumerModule,
        repoModule
    )
}
