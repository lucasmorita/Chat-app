package dev.lmorita

import dev.lmorita.plugins.*
import dev.lmorita.plugins.routes.auth.configureAuthRouting
import dev.lmorita.plugins.routes.configureRouting
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureFrameworks()
    configureSockets()
    configureSerialization()
    configureSecurity()
    configureRouting()
    configureAuthRouting()
    configureDatabases(environment.config)
    configureCORS()
}
