package dev.lmorita.models

import kotlinx.serialization.Serializable

@Serializable
data class MessageResponseJson(val message: String, val username: String, val timestamp: Long)
