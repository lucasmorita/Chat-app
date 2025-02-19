package dev.lmorita.models

import kotlinx.serialization.Serializable

@Serializable
data class ChatMessage(val message: String, val username: String, val timestamp: Long)
