package dev.lmorita

import dev.lmorita.plugins.*
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
    configureDatabases(environment.config)
    configureCORS()
}
