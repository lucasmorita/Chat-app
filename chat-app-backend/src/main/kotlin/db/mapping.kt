package dev.lmorita.db

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

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

object SessionTable : IdTable<String>("chat_session") {
    override val id: Column<EntityID<String>> = text("session_id").entityId()
    val sessionValue = text("session_value")
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)
    val updatedAt = timestamp("updated_at").defaultExpression(CurrentTimestamp)
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}