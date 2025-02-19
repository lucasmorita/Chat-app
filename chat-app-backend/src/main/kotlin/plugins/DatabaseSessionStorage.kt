package dev.lmorita.plugins

import dev.lmorita.db.SessionTable
import dev.lmorita.entities.SessionEntity
import io.ktor.server.sessions.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseSessionStorage : SessionStorage {
    override suspend fun invalidate(id: String) {
        transaction {
            SessionTable.deleteWhere {
                sessionId eq id
            }
        }
    }

    override suspend fun read(id: String): String = transaction {
        SessionEntity.find {
            SessionTable.sessionId eq id
        }.first().sessionValue
    }

    override suspend fun write(id: String, value: String) {
        transaction {
            SessionEntity.new {
                sessionId = id
                sessionValue = value
            }
        }
    }
}