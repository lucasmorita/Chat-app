package dev.lmorita.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.Database

interface TestDatabaseFactory : DatabaseFactory {
    fun close()
}

class H2ConnFactory : TestDatabaseFactory {
    private lateinit var hikari: HikariDataSource

    override fun close() {
        hikari.close()
    }

    override fun connect() {
        val config = HikariConfig().apply {
            driverClassName = "org.h2.Driver"
            username = "sa"
            password = "password"
            jdbcUrl = "jdbc:h2:mem/db"
        }
        val hikariDS = HikariDataSource(config)
        Database.connect(hikariDS)
    }

    companion object {
        fun hikari(): HikariDataSource {
            val config = HikariConfig().apply {
                driverClassName = "org.h2.Driver"
                username = "sa"
                password = "password"
                jdbcUrl = "jdbc:h2:mem:test"
            }
            return HikariDataSource(config)
        }
    }

}