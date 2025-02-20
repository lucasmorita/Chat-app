package dev.lmorita.repository

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.CqlSessionBuilder
import com.datastax.oss.driver.api.querybuilder.QueryBuilder
import dev.lmorita.models.ChatMessage
import java.net.InetSocketAddress
import java.time.Instant
import java.util.*

class ChatMessageRepository {

    private val cqlSession: CqlSession = CqlSessionBuilder()
        .addContactPoint(InetSocketAddress("localhost", 9042))
        .withLocalDatacenter("datacenter1")
        .withKeyspace("chat")
        .build()

    fun saveMessage(chatMessage: ChatMessage) {
        cqlSession.use { session ->
            val insert = QueryBuilder.insertInto("chat", "message")
                .value("chat_id", QueryBuilder.bindMarker())
                .value("user", QueryBuilder.bindMarker())
                .value("created_at", QueryBuilder.bindMarker())
                .value("message", QueryBuilder.bindMarker())
                .build()
            val preparedStmt = session.prepare(insert).bind()
                .setUuid("chat_id", UUID.randomUUID())
                .setString("user", chatMessage.username)
                .setString("message", chatMessage.message)
                .setInstant("created_at", Instant.now())
            session.execute(preparedStmt)
        }
    }

}