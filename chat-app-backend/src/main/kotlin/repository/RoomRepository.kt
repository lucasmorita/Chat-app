package dev.lmorita.repository

import dev.lmorita.db.RoomTable
import dev.lmorita.db.UserAccountTable
import dev.lmorita.entities.RoomEntity
import dev.lmorita.entities.UserAccountEntity
import dev.lmorita.models.room.Room
import dev.lmorita.models.room.RoomRequest
import dev.lmorita.models.user.User
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class RoomRepository {
    fun createRoom(owner: String, roomRequest: RoomRequest) = transaction {
        RoomEntity.new {
            this.name = roomRequest.name
            this.description = roomRequest.description.toString()
            this.usersCount = 0
            this.owner = UserAccountEntity.find {
                UserAccountTable.username eq owner
            }.first().username
        }
    }

    fun getRooms(): List<Room> {
        return transaction {
            RoomTable
                .join(
                    UserAccountTable,
                    JoinType.INNER,
                    onColumn = RoomTable.owner,
                    otherColumn = UserAccountTable.username
                )
                .selectAll()
                .limit(20)
                .map {
                    Room(
                        id = it[RoomTable.id].value,
                        name = it[RoomTable.name],
                        owner = User(it[UserAccountTable.username]),
                        description = it[RoomTable.description],
                    )
                }
        }
    }

    fun getRoom(roomId: Int): Room? = RoomEntity.findById(roomId)?.let {
        Room(
            id = it.id.value,
            name = it.name,
            description = it.description,
            owner = User(it.owner)
        )
    }
}