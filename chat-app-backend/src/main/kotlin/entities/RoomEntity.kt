package dev.lmorita.entities

import dev.lmorita.db.RoomTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class RoomEntity(id: EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<RoomEntity>(RoomTable)
    var name: String by RoomTable.name
    var usersCount: Int by RoomTable.usersCount
    var description: String by RoomTable.description
    var owner: EntityID<Int> by RoomTable.owner
}
