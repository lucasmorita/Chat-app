package dev.lmorita.model

import kotlinx.serialization.Serializable
import org.mindrot.jbcrypt.BCrypt

@Serializable
data class User(
    val username: String,
    private val password: String
) {
    val hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt())
}
