package dev.lmorita.model

interface UserRepository {
    fun addUser(user: User)
}