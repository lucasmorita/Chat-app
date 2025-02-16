package dev.lmorita.db

import org.jetbrains.exposed.dao.id.IntIdTable

object UserAccountTable : IntIdTable("user_account", columnName = "user_id") {
    val username = varchar("username", 50)
    val password = varchar("password", 255)
}

object RoomTable : IntIdTable(columnName = "room_id") {
    val name = varchar("name", length = 100)
    val description = varchar("description", length = 100)
    val usersCount = integer("users_count")
    val owner = reference("owner", UserAccountTable.username)
}

