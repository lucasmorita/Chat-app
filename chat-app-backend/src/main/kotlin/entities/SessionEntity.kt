package dev.lmorita.entities

import dev.lmorita.db.SessionTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class SessionEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<SessionEntity>(SessionTable)
    var sessionId by SessionTable.sessionId
    var sessionValue by SessionTable.sessionValue
    var createdAt by SessionTable.createdAt
    var updatedAt by SessionTable.updatedAt
}