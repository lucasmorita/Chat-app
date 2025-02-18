package dev.lmorita.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureCORS() {
    install(CORS) {
        allowHost("localhost:5173", listOf("http", "https", "ws"))
        allowHost("0.0.0.0:5173", listOf("http", "https", "ws"))
        allowOrigins {
            it == "http://localhost:5173" || it == "http://0.0.0.0:5173"
        }
        allowHeader(HttpHeaders.ContentType)

        allowCredentials = true
        allowMethod(HttpMethod.Post)
    }
}