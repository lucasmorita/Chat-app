package dev.lmorita.services

import dev.lmorita.db.UserAccountTable
import dev.lmorita.entities.UserAccountEntity
import dev.lmorita.models.user.UserAccount
import dev.lmorita.repository.UserRepository
import org.jetbrains.exposed.sql.transactions.transaction

class UserService(
    private val userRepository: UserRepository
) {
    fun createUser(userAccount: UserAccount): UserAccountEntity = userRepository.createUser(userAccount)
    fun isNewUser(userAccount: UserAccount): Boolean = transaction {
        UserAccountEntity.find {
            UserAccountTable.username eq userAccount.username
        }.empty()
    }
}