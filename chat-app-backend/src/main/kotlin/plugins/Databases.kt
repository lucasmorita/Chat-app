package dev.lmorita.plugins

import io.ktor.server.application.*
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabases(config: ApplicationConfig) {
    val url = config.property("storage.jdbcURL").getString()
    val driver = config.property("storage.driverClassName").getString()
    val user = config.property("storage.user").getString()
    val password = config.property("storage.password").getString()
    Database.connect(
        url = url,
        driver = driver,
        user = user,
        password = password
    )
}