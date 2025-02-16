package dev.lmorita.entities

import dev.lmorita.db.UserAccountTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserAccountEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserAccountEntity>(UserAccountTable)
    var username by UserAccountTable.username
    var password by UserAccountTable.password
}