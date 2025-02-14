package dev.lmorita

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabases() {
    Database.connect(
        url = "jdbc:postgresql://192.168.15.3:5432/postgres",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "password"
    )
}