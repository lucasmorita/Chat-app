package dev.lmorita.services

import dev.lmorita.models.room.Room
import dev.lmorita.models.room.RoomList
import dev.lmorita.models.room.RoomRequest
import dev.lmorita.repository.RoomRepository

class RoomService(
    private val roomRepository: RoomRepository
) {
    fun createRoom(username: String, roomRequest: RoomRequest) = roomRepository.createRoom(username, roomRequest)
    fun getRooms(): RoomList = roomRepository.getRooms()
    fun getRoom(roomId: Int): Room? = roomRepository.getRoom(roomId)
}