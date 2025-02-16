package dev.lmorita.models.user

import kotlinx.serialization.Serializable
import org.mindrot.jbcrypt.BCrypt

@Serializable
data class UserAccount(
    val username: String,
    private val password: String
) {
    val hashedPassword: String = BCrypt.hashpw(password, BCrypt.gensalt())
}
