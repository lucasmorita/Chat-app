package dev.lmorita.models.room

import dev.lmorita.models.user.User
import kotlinx.serialization.Serializable

@Serializable
data class RoomList(val rooms: List<Room>)

@Serializable
data class Room(
    val id: Int,
    val name: String,
    val usersQuantity: Int = 0,
    val description: String? = null,
    val owner: User? = null,
)

@Serializable
data class RoomRequest(
    val name: String,
    val usersQuantity: Int = 0,
    val description: String? = null,
)