group = AppConfig.group
version = AppConfig.versionName

plugins {
    kotlin("jvm") version Versions.kotlinPluginVersion
    id("io.ktor.plugin") version Versions.ktorPluginVersion
    id("org.jetbrains.kotlin.plugin.serialization") version Versions.serializationVersion
}

application {
    mainClass.set("com.example.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    // Core
    implementation(Libs.ktorNetty)
    implementation(Libs.ktorAuth)
    implementation(Libs.ktorJwt)
    implementation(Libs.ktorLogging)
    implementation(Libs.ktorStatusPages)
    implementation(Libs.ktorMetricsMicrometer)
    implementation(Libs.logback)

    // Serialization
    implementation(Libs.ktorContentNegotiation)
    implementation(Libs.ktorSerializationGson)
    implementation(Libs.ktorSerializationJson)

    // Koin
    implementation(Libs.koin)
    implementation(Libs.koinLogger)
    implementation(Libs.koinAnnotations)

    // Cors
    implementation(Libs.cors)
    implementation(Libs.ktorFeatures)

    // Result
    implementation(Libs.result)

    // HTTP Client
    implementation(Libs.clientCore)
    implementation(Libs.clientJson)
    implementation(Libs.clientSerialization)

    // JWT
    implementation(Libs.ktorJwtAuthJvm)
    implementation(Libs.ktorJwtServerAuth)
    implementation(Libs.ktorJwtCommon)

    // Role Based Auth
    implementation(Libs.roleBasedAuth)

    // Websockets
    implementation(Libs.webSockets)
    implementation(Libs.ktorSesions)
}
