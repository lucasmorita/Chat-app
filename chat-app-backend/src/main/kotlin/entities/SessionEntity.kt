package dev.lmorita.entities

import dev.lmorita.db.SessionTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

class SessionEntity(id: EntityID<String>): Entity<String>(id) {
    companion object : EntityClass<String, SessionEntity>(SessionTable)
    var sessionValue by SessionTable.sessionValue
    var createdAt by SessionTable.createdAt
    var updatedAt by SessionTable.updatedAt
}