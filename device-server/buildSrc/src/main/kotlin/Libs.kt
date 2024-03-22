object Libs {
    // Dependencies
    val ktorNetty = "io.ktor:ktor-server-netty:${Versions.ktorVersion}"
    val logback = "ch.qos.logback:logback-classic:${Versions.logbackVersion}"
    val ktorAuth = "io.ktor:ktor-server-auth:${Versions.ktorVersion}"
    val ktorLogging = "io.ktor:ktor-server-call-logging:${Versions.ktorVersion}"
    val ktorStatusPages = "io.ktor:ktor-server-status-pages:${Versions.ktorVersion}"
    val ktorContentNegotiation = "io.ktor:ktor-server-content-negotiation:${Versions.ktorVersion}"
    val ktorSerializationGson = "io.ktor:ktor-serialization-gson:${Versions.ktorVersion}"
    val ktorSerializationJson = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktorVersion}"
    val ktorMetricsMicrometer = "io.ktor:ktor-server-metrics-micrometer:${Versions.ktorVersion}"
    val ktorJwt = "io.ktor:ktor-server-auth-jwt:${Versions.ktorVersion}"

    // Exposed
    val exposedCore = "org.jetbrains.exposed:exposed-core:${Versions.exposedVersion}"
    val exposedDao = "org.jetbrains.exposed:exposed-dao:${Versions.exposedVersion}"
    val exposedJdbc = "org.jetbrains.exposed:exposed-jdbc:${Versions.exposedVersion}"
    val exposedDateTime = "org.jetbrains.exposed:exposed-java-time:${Versions.exposedVersion}"

    // Hikari
    val hikari = "com.zaxxer:HikariCP:${Versions.hikariVersion}"

    val bcrypt = "org.mindrot:jbcrypt:${Versions.bcryptVersion}"

    // CORS
    val cors = "io.ktor:ktor-server-cors:${Versions.ktorVersion}"

    // Koin
    val koin = "io.insert-koin:koin-ktor:${Versions.koinKtorVersion}"
    val koinLogger = "io.insert-koin:koin-logger-slf4j:${Versions.koinKtorVersion}"
    val koinAnnotations = "io.insert-koin:koin-annotations:${Versions.koinKspVersion}"
    val koinKspCompiler = "io.insert-koin:koin-ksp-compiler:${Versions.koinKspVersion}"

    // Result
    const val result = "com.michael-bull.kotlin-result:kotlin-result:${Versions.kotlinResult}"

    // HTTP REQUEST
    const val clientCore = "io.ktor:ktor-client-core:${Versions.ktorVersion}"
    const val clientJson = "io.ktor:ktor-client-json:${Versions.ktorVersion}"
    const val clientSerialization = "io.ktor:ktor-client-serialization:${Versions.ktorVersion}"

    // POSTGRES
    val postgreKtorm = "org.ktorm:ktorm-support-postgresql:${Versions.ktormVersion}"
    val postgreSQL = "org.postgresql:postgresql:${Versions.postgresDriverVersion}"

    // Micrometer Prometeus
    val micrometerPrometeus = "io.micrometer:micrometer-registry-prometheus:${Versions.micrometerPrometeusVersion}"

    // Testing
    val koinTest = "io.insert-koin:koin-test:${Versions.koinVersion}"
    val ktorServerTest = "io.ktor:ktor-server-test-host:${Versions.ktorVersion}"
    val assertJ = "org.assertj:assertj-core:${Versions.assertJVersion}"
    val junit = "org.junit.jupiter:junit-jupiter:${Versions.junit}"
    val junitEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junit}"
    val mockK = "io.mockk:mockk:${Versions.mockK}"
}