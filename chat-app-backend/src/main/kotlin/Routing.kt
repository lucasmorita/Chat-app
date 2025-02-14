package dev.lmorita

import dev.lmorita.entities.UserEntity
import dev.lmorita.model.User
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        post("/users") {
            val user = call.receive<User>()
            log.info("User: $user")

            transaction {
                UserEntity.new {
                    username = user.username
                    password = user.hashedPassword
                }
            }
        }
    }
}
