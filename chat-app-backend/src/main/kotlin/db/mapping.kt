package dev.lmorita.db

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object UserAccountTable : IntIdTable("user_account", columnName = "user_id") {
    val username: Column<String> = varchar("username", 50)
    val password: Column<String> = varchar("password", 255)
}

object RoomTable : IntIdTable(columnName = "room_id") {
    val name: Column<String> = varchar("name", length = 100)
    val description: Column<String> = varchar("description", length = 100)
    val usersCount: Column<Int> = integer("users_count")
    val owner: Column<EntityID<Int>> = reference("owner", UserAccountTable.id)
}

