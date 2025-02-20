package fixtures

import dev.lmorita.plugins.routes.configureRouting
import dev.lmorita.plugins.configureSecurity
import dev.lmorita.plugins.configureSerialization
import dev.lmorita.plugins.routes.auth.configureAuthRouting
import dev.lmorita.repository.RoomRepository
import dev.lmorita.repository.UserRepository
import dev.lmorita.services.RoomService
import dev.lmorita.services.UserService
import io.ktor.server.testing.*
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

fun baseTestApplication(block: suspend ApplicationTestBuilder.() -> Unit) {
    testApplication {
        application {
            koin {
                modules(module {
                    single { RoomRepository() }
                    single { RoomService(get()) }
                    single { UserRepository() }
                    single { UserService(get()) }
                })
            }
            configureSecurity()
            configureRouting()
            configureAuthRouting()
            configureSerialization()
        }
        block()
    }
}