package dev.lmorita.plugins

import dev.lmorita.db.PostgresConnFactory
import io.ktor.server.application.*
import io.ktor.server.config.*

fun Application.configureDatabases(config: ApplicationConfig) {
    PostgresConnFactory(config).connect()
}