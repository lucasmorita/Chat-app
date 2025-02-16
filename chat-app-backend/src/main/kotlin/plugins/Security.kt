package dev.lmorita.plugins

import dev.lmorita.db.UserAccountTable
import dev.lmorita.entities.UserAccountEntity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.engine.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt


fun Application.configureSecurity() {
    install(Sessions) {
        val secretSignKey = hex("6819b57a326945c1968f45236589")
        cookie<UserSession>("user_session", SessionStorageMemory()) {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 8640
            cookie.extensions["SameSite"] = "lax"
            cookie.extensions["HttpOnly"] = "true"

            transform(SessionTransportTransformerMessageAuthentication(secretSignKey))
        }
    }
    authentication {
        session<UserSession>("auth-session") {
            validate { session ->
                sessions.get("user_session").toString() == session.name
            }
            challenge {
                call.respondRedirect("/login")
            }
        }
        basic(name = "myauth1") {
            realm = "Ktor Server"
            validate { credentials ->
                if (credentials.name == credentials.password) {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
        form(name = "auth-form") {
            userParamName = "username"
            passwordParamName = "password"
            validate { credential ->
                val entity = transaction {
                    UserAccountEntity.find {
                        UserAccountTable.username eq credential.name
                    }.firstOrNull()
                }
                entity?.let {
                    if (BCrypt.checkpw(credential.password, it.password)) {
                        UserIdPrincipal(credential.name)
                    } else {
                        null
                    }
                }
            }
            challenge {
                call.respond(HttpStatusCode.Unauthorized)
            }
        }
    }
    routing {
//        get("/session/increment") {
//            val session = call.sessions.get<UserSession>() ?: UserSession()
//            call.sessions.set(session.copy(count = session.count + 1))
//            call.respondText("Counter is ${session.count}. Refresh to increment.")
//        }
        authenticate("myauth1") {
            get("/protected/route/basic") {
                val principal = call.principal<UserIdPrincipal>()!!
                call.respondText("Hello ${principal.name}")
            }
        }
//        authenticate("myauth2") {
//            get("/protected/route/form") {
//                val principal = call.principal<UserIdPrincipal>()!!
//                call.respondText("Hello ${principal.name}")
//            }
//        }
    }
}
@Serializable
data class UserSession(val name: String)
