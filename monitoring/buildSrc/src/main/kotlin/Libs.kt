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

    // EXPOSED
    const val exposedCore = "org.jetbrains.exposed:exposed-core:${Versions.exposedVersion}"
    const val exposedDao = "org.jetbrains.exposed:exposed-dao:${Versions.exposedVersion}"
    const val exposedJdbc = "org.jetbrains.exposed:exposed-jdbc:${Versions.exposedVersion}"
    const val exposedDateTime = "org.jetbrains.exposed:exposed-java-time:${Versions.exposedVersion}"

    // HIKARI
    const val hikari = "com.zaxxer:HikariCP:${Versions.hikariVersion}"

    const val bcrypt = "org.mindrot:jbcrypt:${Versions.bcryptVersion}"

    // CORS
    const val cors = "io.ktor:ktor-server-cors:${Versions.ktorVersion}"

    // Koin
    const val koin = "io.insert-koin:koin-ktor:${Versions.koinKtorVersion}"
    const val koinLogger = "io.insert-koin:koin-logger-slf4j:${Versions.koinKtorVersion}"
    const val koinAnnotations = "io.insert-koin:koin-annotations:${Versions.koinKspVersion}"

    // RESULT
    const val result = "com.michael-bull.kotlin-result:kotlin-result:${Versions.kotlinResultVersion}"

    // HTTP REQUEST
    const val clientCore = "io.ktor:ktor-client-core:${Versions.ktorVersion}"
    const val clientJson = "io.ktor:ktor-client-json:${Versions.ktorVersion}"
    const val clientSerialization = "io.ktor:ktor-client-serialization:${Versions.ktorVersion}"

    // POSTGRES
    const val postgreKtorm = "org.ktorm:ktorm-support-postgresql:${Versions.ktormVersion}"
    const val postgreSQL = "org.postgresql:postgresql:${Versions.postgresDriverVersion}"

    // RABBIT MQ
    const val rabbitMqClient = "com.rabbitmq:amqp-client:${Versions.rabbitMqClientVersion}"

    // SLF4J
    const val slf4jSimple = "org.slf4j:slf4j-simple:${Versions.slf4jVersion}"
    const val slf4jApi = "org.slf4j:slf4j-api:${Versions.slf4jVersion}"

    // WEBSOCKETS
    const val webSockets = "io.ktor:ktor-server-websockets:${Versions.ktorVersion}"
}