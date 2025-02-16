package dev.lmorita.entities

import dev.lmorita.db.RoomTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class RoomEntity(id: EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<RoomEntity>(RoomTable)
    var name by RoomTable.name
    var usersCount by RoomTable.usersCount
    var description by RoomTable.description
    var owner by RoomTable.owner
}
