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

    // Exposed
    implementation(Libs.exposedCore)
    implementation(Libs.exposedDao)
    implementation(Libs.exposedDateTime)
    implementation(Libs.exposedJdbc)

    // Hikari
    implementation(Libs.hikari)

    // Postgres
    implementation(Libs.postgreSQL)
    implementation(Libs.postgreKtorm)

    // Bcrypt
    implementation(Libs.bcrypt)

    // Koin
    implementation(Libs.koin)
    implementation(Libs.koinLogger)
    implementation(Libs.koinAnnotations)

    // Cors
    implementation(Libs.cors)

    // Result
    implementation(Libs.result)

    // HTTP Client
    implementation(Libs.clientCore)
    implementation(Libs.clientJson)
    implementation(Libs.clientSerialization)

    // Rabbit Mq
    implementation(Libs.rabbitMqClient)

    // WebSockets
    implementation(Libs.webSockets)
}
