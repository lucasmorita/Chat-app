package endpoints

import com.google.gson.Gson
import dev.lmorita.models.ChatMessage
import dev.lmorita.plugins.configureSockets
import io.ktor.client.plugins.websocket.*
import io.ktor.server.testing.*
import io.ktor.websocket.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.koin.test.KoinTest
import java.time.Instant
import kotlin.test.Test

class WebSocketTest : KoinTest {

    @Test
    fun `should send message to room 1`() = testApplication {
        application {
            configureSockets()
        }
        val client = createClient {
            install(WebSockets)
        }
        client.webSocket("/rooms/1/chat") {
            val unixTime = Instant.now().epochSecond
            val chatMessage = ChatMessage("hello", "mock", unixTime)
            val chatString = Gson().toJson(chatMessage)
            send(chatString)
            val responseText = (incoming.receive() as Frame.Text).readText()
            assertEquals("{\"message\":\"hello\",\"username\":\"mock\",\"timestamp\":$unixTime}", responseText)
        }
    }
}