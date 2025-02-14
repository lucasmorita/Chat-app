package dev.lmorita.entities

import dev.lmorita.db.UsersTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserEntity>(UsersTable)

    var user_id by UsersTable.userId
    var username by UsersTable.username
    var password by UsersTable.password
}