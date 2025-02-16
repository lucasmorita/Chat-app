package dev.lmorita.repository

import dev.lmorita.entities.UserAccountEntity
import dev.lmorita.models.user.UserAccount
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository {
    fun createUser(user: UserAccount) = transaction {
        UserAccountEntity.new {
            username = user.username
            password = user.hashedPassword
        }
    }
}