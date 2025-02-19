package dev.lmorita.plugins

import com.google.gson.Gson
import dev.lmorita.models.ChatMessage
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap
import kotlin.time.Duration.Companion.seconds

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = 15.seconds
        timeout = 15.seconds
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    routing {
        val roomSessions = ConcurrentHashMap<Int, MutableSharedFlow<ChatMessage>>()
        val gson = Gson()
        webSocket("/rooms/{id}/chat") {
            val id = call.parameters["id"]!!.toInt()

            val sessions = roomSessions.computeIfAbsent(id) {
                MutableSharedFlow(
                    extraBufferCapacity = 20000
                )
            }
            log.info("Joined room $id! current connected sessions: ${sessions.subscriptionCount.value}")
            val job = launch {
                val roomSession = roomSessions[id]!!
                roomSession.asSharedFlow().collect {
                    log.info("send message $it")
                    send(gson.toJson(it))
                }
            }
            runCatching {
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val message = gson.fromJson(frame.readText(), ChatMessage::class.java)
                    log.info("message consumed: $message")
                    roomSessions[id]!!.emit(message)
                }
            }.onFailure { exception ->
                println("WebSocket exception ${exception.localizedMessage}")
            }.also {
                job.cancel()
            }
        }
    }
}
