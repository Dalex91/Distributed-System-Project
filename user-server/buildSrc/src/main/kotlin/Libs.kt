object Libs {
    // Dependencies
    const val ktorNetty = "io.ktor:ktor-server-netty:${Versions.ktorVersion}"
    const val ktorServerCore = "io.ktor:ktor-server-core-jvm:${Versions.ktorVersion}"
    const val logback = "ch.qos.logback:logback-classic:${Versions.logbackVersion}"
    const val ktorAuth = "io.ktor:ktor-auth:${Versions.ktorAuth}"
    const val ktorServerAuth = "io.ktor:ktor-server-auth:${Versions.ktorVersion}"
    const val ktorLogging = "io.ktor:ktor-server-call-logging:${Versions.ktorVersion}"
    const val ktorStatusPages = "io.ktor:ktor-server-status-pages:${Versions.ktorVersion}"
    const val ktorContentNegotiation = "io.ktor:ktor-server-content-negotiation:${Versions.ktorVersion}"
    const val ktorSerializationGson = "io.ktor:ktor-serialization-gson:${Versions.ktorVersion}"
    const val ktorSerializationJson = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktorVersion}"
    const val ktorMetricsMicrometer = "io.ktor:ktor-server-metrics-micrometer:${Versions.ktorVersion}"

    // EXPOSED
    const val exposedCore = "org.jetbrains.exposed:exposed-core:${Versions.exposedVersion}"
    const val exposedDao = "org.jetbrains.exposed:exposed-dao:${Versions.exposedVersion}"
    const val exposedJdbc = "org.jetbrains.exposed:exposed-jdbc:${Versions.exposedVersion}"
    const val exposedDateTime = "org.jetbrains.exposed:exposed-java-time:${Versions.exposedVersion}"

    // HIKARI
    const val hikari = "com.zaxxer:HikariCP:${Versions.hikariVersion}"

    // BCRYPT
    const val bCrypt = "com.ToxicBakery.library.bcrypt:bcrypt:${Versions.bcryptVersion}"

    // ROLE BASED AUTH
    const val roleBasedAuth = "com.github.omkar-tenkale:ktor-role-based-auth:${Versions.roleBasedAuth}"

    // JWT Authentication
    const val ktorJwtAuthJvm = "io.ktor:ktor-server-auth-jvm:${Versions.ktorVersion}"
    const val ktorJwtServerAuth = "io.ktor:ktor-server-auth-jwt-jvm:${Versions.ktorVersion}"
    const val ktorJwtCommon = "io.ktor:ktor-server-host-common-jvm:${Versions.ktorVersion}"

    // RESULT
    const val result = "com.michael-bull.kotlin-result:kotlin-result:${Versions.kotlinResult}"

    // CORS
    const val cors = "io.ktor:ktor-server-cors:${Versions.ktorVersion}"

    // KOIN
    const val koin = "io.insert-koin:koin-ktor:${Versions.koinKtorVersion}"
    const val koinLogger = "io.insert-koin:koin-logger-slf4j:${Versions.koinKtorVersion}"
    const val koinAnnotations = "io.insert-koin:koin-annotations:${Versions.koinKspVersion}"

    // HTTP REQUEST
    const val clientCore = "io.ktor:ktor-client-core:${Versions.ktorVersion}"
    const val clientJson = "io.ktor:ktor-client-json:${Versions.ktorVersion}"
    const val clientOKHTTP = "io.ktor:ktor-client-okhttp:${Versions.ktorVersion}"
    const val clientSerialization = "io.ktor:ktor-client-serialization:${Versions.ktorVersion}"
    const val clientCIO = "io.ktor:ktor-client-cio:${Versions.ktorVersion}"

    // POSTGRES
    const val postgreKtorm = "org.ktorm:ktorm-support-postgresql:${Versions.ktormVersion}"
    const val postgreSQL = "org.postgresql:postgresql:${Versions.postgresDriverVersion}"

    // Testing
    const val koinTest = "io.insert-koin:koin-test:${Versions.koinVersion}"
    const val ktorServerTest = "io.ktor:ktor-server-test-host:${Versions.ktorVersion}"
    const val assertJ = "org.assertj:assertj-core:${Versions.assertJVersion}"
    const val junit = "org.junit.jupiter:junit-jupiter:${Versions.junit}"
    const val junitEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junit}"
    const val mockK = "io.mockk:mockk:${Versions.mockK}"
}