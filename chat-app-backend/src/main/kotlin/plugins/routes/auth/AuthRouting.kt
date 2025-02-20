package dev.lmorita.plugins.routes.auth

import dev.lmorita.plugins.UserSession
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Application.configureAuthRouting() {
    routing {
        route("/api/auth") {
            authenticate("auth-form") {
                post("/login") {
                    val username = call.principal<UserIdPrincipal>()?.name.toString()
                    call.sessions.set(UserSession(username))
                    call.respond(HttpStatusCode.OK)
                }
            }
            authenticate("auth-session") {
                delete("/logout") {
                    application.log.info("current_session=${call.sessions}")
                    call.sessions.clear<UserSession>()
                    call.respond(HttpStatusCode.OK)
                }
                get("/session") {
                    call.sessions.get<UserSession>()?.let {
                        call.respond(HttpStatusCode.OK, it)
                    }
                }
            }
        }
    }
}