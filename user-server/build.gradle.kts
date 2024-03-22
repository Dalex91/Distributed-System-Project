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
    implementation(Libs.ktorServerCore)
    implementation(Libs.ktorAuth)
    implementation(Libs.ktorServerAuth)
    implementation(Libs.ktorLogging)
    implementation(Libs.ktorStatusPages)
    implementation(Libs.ktorMetricsMicrometer)
    implementation(Libs.logback)

    // Serialization
    implementation(Libs.ktorContentNegotiation)
    implementation(Libs.ktorSerializationGson)
    implementation(Libs.ktorSerializationJson)

    //Exposed
    implementation(Libs.exposedCore)
    implementation(Libs.exposedDao)
    implementation(Libs.exposedJdbc)
    implementation(Libs.exposedDateTime)

    // Hikari
    implementation(Libs.hikari)

    // BCrypt
    implementation(Libs.bCrypt)

    // Postgres
    implementation(Libs.postgreSQL)
    implementation(Libs.postgreKtorm)

    // Koin
    implementation(Libs.koin)
    implementation(Libs.koinLogger)
    implementation(Libs.koinAnnotations)

    // JWT
    implementation(Libs.ktorJwtAuthJvm)
    implementation(Libs.ktorJwtServerAuth)
    implementation(Libs.ktorJwtCommon)

    // CORS
    implementation(Libs.cors)

    // Role Based Auth
    implementation(Libs.roleBasedAuth)

    // Result
    implementation(Libs.result)

    // HTTP Client
    implementation(Libs.clientCore)
    implementation(Libs.clientJson)
    implementation(Libs.clientSerialization)
    implementation(Libs.clientCIO)
    implementation(Libs.clientOKHTTP)

    // Koin for Unit tests
    testImplementation(Libs.koinTest)
    testImplementation(Libs.ktorServerTest)
    testImplementation(Libs.assertJ)
    testImplementation(Libs.junit)
    testRuntimeOnly(Libs.junitEngine)
    testImplementation(Libs.mockK)
}
