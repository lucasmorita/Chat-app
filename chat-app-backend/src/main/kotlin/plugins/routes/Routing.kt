package dev.lmorita.plugins.routes

import dev.lmorita.models.room.RoomRequest
import dev.lmorita.models.user.UserAccount
import dev.lmorita.plugins.UserSession
import dev.lmorita.services.RoomService
import dev.lmorita.services.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val roomService by inject<RoomService>()
    val userService by inject<UserService>()
    routing {
        post("/users") {
            val userAccount = call.receive<UserAccount>()

            if (!userService.isNewUser(userAccount)) {
                call.respond(status = HttpStatusCode.Conflict, "Username already taken")
                return@post
            }
            val createdUser = userService.createUser(userAccount)
            log.info("created user: $createdUser")
            call.respond(status = HttpStatusCode.Created, message = "User created")
        }

        authenticate("auth-session") {
            route("/rooms") {
                get {
                    val rooms = roomService.getRooms()
                    log.info("Existing rooms: $rooms")
                    call.respond(rooms)
                }
                get("/{id}") {
                    val room = roomService.getRoom(call.pathParameters["id"]!!.toInt())!!
                    call.respond(room)
                }
                post {
                    val room = call.receive<RoomRequest>()
                    val userSession = call.sessions.get<UserSession>()

                    val createdRoom = roomService.createRoom(userSession!!.name, room)
                    call.respond(HttpStatusCode.Created, "Room created with id ${createdRoom.id}")
                }
            }
        }
    }
}
