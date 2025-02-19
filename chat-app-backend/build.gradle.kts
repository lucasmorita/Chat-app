
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
    id("java-library")
}

group = "dev.lmorita"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.websockets)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.websockets)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.sessions)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.cors)
    implementation(libs.gson)
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.crypt)
    implementation(libs.postgres)
    implementation(libs.jbcrypt)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.koin.test)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.h2)
    testImplementation(libs.hikari)
}

tasks.test {
    useJUnitPlatform()
}