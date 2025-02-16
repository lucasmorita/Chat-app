package dev.lmorita.plugins

import dev.lmorita.models.MessageResponse
import dev.lmorita.services.RoomService
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.ktor.ext.inject
import java.util.concurrent.ConcurrentHashMap
import kotlin.time.Duration.Companion.seconds

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = 15.seconds
        timeout = 2.seconds
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    val roomService by inject<RoomService>()
    routing {
        val roomSessions = ConcurrentHashMap<Int, MutableSharedFlow<MessageResponse>>()
        webSocket("/rooms/{id}/chat") { // websocketSession
            val id = call.parameters["id"]!!.toInt()
            transaction {
                roomService.getRoom(id)
            } ?: close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "Room does not exist"))

            val sessions = roomSessions.computeIfAbsent(id) {
                MutableSharedFlow()
            }
            send("Joined room $id! current connected sessions: ${sessions.subscriptionCount}")
            val job = launch {
                val roomSession = roomSessions[id]!!
                roomSession.asSharedFlow().collect {
                    send(it.message)
                }
            }
            runCatching {
                incoming.consumeEach { frame ->
                    if (frame is Frame.Text) {
                        val receivedText = frame.readText()
                        val messageResponse = MessageResponse(receivedText)
                        roomSessions[id]!!.emit(messageResponse)
                    }
                }
            }.onFailure { exception ->
                println("WebSocket exception ${exception.localizedMessage}")
            }.also {
                job.cancel()
            }
        }

    }
}
