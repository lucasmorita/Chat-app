package dev.lmorita.plugins

import dev.lmorita.db.UserAccountTable
import dev.lmorita.entities.UserAccountEntity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt
import kotlin.collections.firstOrNull
import kotlin.collections.set


fun Application.configureSecurity() {
    install(Sessions) {
        val secretSignKey = hex("6819b57a326945c1968f45236589")
        cookie<UserSession>("user_session", SessionStorageMemory()) {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 8640
            cookie.extensions["SameSite"] = "lax"
            cookie.httpOnly = false
            transform(SessionTransportTransformerMessageAuthentication(secretSignKey))
        }
    }
    authentication {
        session<UserSession>("auth-session") {
            validate { session ->
                println("validating session, sessions: $sessions with $session")
                sessions.get("user_session").toString() == session.name
            }
            challenge {
                call.respond(HttpStatusCode.Unauthorized, "$it")
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
}
@Serializable
data class UserSession(val name: String)
