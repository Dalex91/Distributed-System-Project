package device.example.plugins

import device.example.configuration.ServerModules
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
    install(Koin) {
        modules(ServerModules.modules)
    }
}
