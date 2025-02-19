package dev.lmorita.plugins

import dev.lmorita.db.SessionTable
import dev.lmorita.entities.SessionEntity
import io.ktor.server.sessions.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.exposedLogger
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.concurrent.ConcurrentHashMap

class DatabaseSessionStorage : SessionStorage {
    private val cache: MutableMap<String, String> = ConcurrentHashMap()

    override suspend fun invalidate(id: String) {
        cache.remove(id)
        transaction {
            SessionTable.deleteWhere {
                this.id eq id
            }
        }
    }

    override suspend fun read(id: String): String {
        exposedLogger.debug("read_session_storage, cache_hit=${cache[id] != null}")
        return cache.computeIfAbsent(id) {
            transaction {
                SessionEntity.find {
                    SessionTable.id eq id
                }.first().sessionValue
            }
        }
    }

    override suspend fun write(id: String, value: String) {
        if (cache.contains(id)) {
            return
        }
        transaction {
            SessionEntity.findById(id)
                ?: run {
                    SessionEntity.new(id = id) {
                        sessionValue = value
                    }
                    cache[id] = value
                }
        }
    }
}