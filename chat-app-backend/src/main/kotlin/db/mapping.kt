package dev.lmorita.db

import org.jetbrains.exposed.dao.id.IntIdTable

object UsersTable : IntIdTable("users") {
    val userId = integer("user_id")
    val username = varchar("username", 50)
    val password = varchar("password", 255)
}