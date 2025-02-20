package dev.lmorita.plugins

import dev.lmorita.repository.ChatMessageRepository
import dev.lmorita.repository.RoomRepository
import dev.lmorita.repository.UserRepository
import dev.lmorita.services.RoomService
import dev.lmorita.services.UserService
import io.ktor.server.application.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureFrameworks() {
    install(Koin) {
        slf4jLogger()
        modules(module {
            singleOf(::ChatMessageRepository)
            singleOf(::RoomRepository)
            singleOf(::RoomService)
            singleOf(::UserRepository)
            singleOf(::UserService)
        })
    }
}
