group = AppConfig.group
version = AppConfig.versionName

val ktorm_version: String by project
val postgresql_driver_version: String by project

plugins {
    kotlin("jvm") version Versions.kotlinPluginVersion
    id("io.ktor.plugin") version Versions.ktorPluginVersion
    id("org.jetbrains.kotlin.plugin.serialization") version Versions.serializationVersion
}

application {
    mainClass.set("device.example.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {

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

    //Exposed
    implementation(Libs.exposedCore)
    implementation(Libs.exposedDao)
    implementation(Libs.exposedJdbc)
    implementation(Libs.exposedDateTime)

    // Hikari
    implementation(Libs.hikari)

    // Postgres
    implementation(Libs.postgreSQL)
    implementation(Libs.postgreKtorm)

    // Bcrypt
    implementation(Libs.bcrypt)
    implementation(Libs.micrometerPrometeus)

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
}
