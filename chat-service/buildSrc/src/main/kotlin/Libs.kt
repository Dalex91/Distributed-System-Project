object Libs {
    // Dependencies
    const val ktorNetty = "io.ktor:ktor-server-netty:${Versions.ktorVersion}"
    const val logback = "ch.qos.logback:logback-classic:${Versions.logbackVersion}"
    const val ktorAuth = "io.ktor:ktor-server-auth:${Versions.ktorVersion}"
    const val ktorLogging = "io.ktor:ktor-server-call-logging:${Versions.ktorVersion}"
    const val ktorStatusPages = "io.ktor:ktor-server-status-pages:${Versions.ktorVersion}"
    const val ktorContentNegotiation = "io.ktor:ktor-server-content-negotiation:${Versions.ktorVersion}"
    const val ktorSerializationGson = "io.ktor:ktor-serialization-gson:${Versions.ktorVersion}"
    const val ktorSerializationJson = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktorVersion}"
    const val ktorMetricsMicrometer = "io.ktor:ktor-server-metrics-micrometer:${Versions.ktorVersion}"
    const val ktorJwt = "io.ktor:ktor-server-auth-jwt:${Versions.ktorVersion}"


    // CORS
    const val cors = "io.ktor:ktor-server-cors:${Versions.ktorVersion}"
    const val ktorFeatures = "io.ktor:ktor-features:${Versions.ktorFeatures}"

    // KOIN
    const val koin = "io.insert-koin:koin-ktor:${Versions.koinKtorVersion}"
    const val koinLogger = "io.insert-koin:koin-logger-slf4j:${Versions.koinKtorVersion}"
    const val koinAnnotations = "io.insert-koin:koin-annotations:${Versions.koinKspVersion}"

    // JWT
    const val ktorJwtAuthJvm = "io.ktor:ktor-server-auth-jvm:${Versions.ktorVersion}"
    const val ktorJwtServerAuth = "io.ktor:ktor-server-auth-jwt-jvm:${Versions.ktorVersion}"
    const val ktorJwtCommon = "io.ktor:ktor-server-host-common-jvm:${Versions.ktorVersion}"

    // WEBSOCKETS
    const val webSockets = "io.ktor:ktor-server-websockets:${Versions.ktorVersion}"
    const val ktorSesions = "io.ktor:ktor-server-sessions:${Versions.ktorVersion}"

    // ROLE BASED AUTH
    const val roleBasedAuth = "com.github.omkar-tenkale:ktor-role-based-auth:${Versions.roleBasedAuth}"

    // RESULT
    const val result = "com.michael-bull.kotlin-result:kotlin-result:${Versions.kotlinResult}"

    // HTTP REQUEST
    const val clientCore = "io.ktor:ktor-client-core:${Versions.ktorVersion}"
    const val clientJson = "io.ktor:ktor-client-json:${Versions.ktorVersion}"
    const val clientSerialization = "io.ktor:ktor-client-serialization:${Versions.ktorVersion}"
}