package dev.lmorita

import dev.lmorita.db.H2ConnFactory
import dev.lmorita.db.RoomTable
import dev.lmorita.db.UserAccountTable
import dev.lmorita.entities.RoomEntity
import dev.lmorita.entities.UserAccountEntity
import dev.lmorita.models.room.Room
import dev.lmorita.models.room.RoomList
import dev.lmorita.models.room.RoomRequest
import dev.lmorita.models.user.User
import dev.lmorita.models.user.UserAccount
import dev.lmorita.plugins.configureRouting
import dev.lmorita.plugins.configureSecurity
import dev.lmorita.plugins.configureSerialization
import dev.lmorita.repository.RoomRepository
import dev.lmorita.repository.UserRepository
import dev.lmorita.services.RoomService
import dev.lmorita.services.UserService
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.dsl.module
import org.koin.ktor.plugin.koin
import org.koin.test.KoinTest
import org.mindrot.jbcrypt.BCrypt
import kotlin.test.Test
import kotlin.test.assertEquals

class RouteTest : KoinTest {

    private val database = Database.connect(H2ConnFactory.hikari())

    init {
        transaction(database) {
            SchemaUtils.create(UserAccountTable)
            SchemaUtils.create(RoomTable)
            val userAccount = UserAccountEntity.new {
                username = "MockUser"
                password = BCrypt.hashpw("123", BCrypt.gensalt())
            }
            RoomEntity.new {
                name = "mock"
                description = "mock room"
                this.usersCount = 0
                this.owner = userAccount.id
            }
        }
    }

    @Test
    fun `should receive Unauthorized if not authed when GET rooms`() = baseTestApplication {
        client.get("/rooms").run {
            assertEquals(HttpStatusCode.Unauthorized, status)
        }
    }

    @Test
    fun `should receive OK if user is authenticated when GET rooms`() = baseTestApplication {
        val client = createClient {
            install(HttpCookies)
            install(ContentNegotiation) {
                json()
            }
        }
        client.post("/login") {
            contentType(ContentType.Application.FormUrlEncoded)
            setBody("username=MockUser&password=123")
        }.also {
            assertEquals(HttpStatusCode.OK, it.status)
        }
        client.get("/rooms").run {
            assertEquals(HttpStatusCode.OK, status)
            val rooms = body<RoomList>()
            assertEquals(
                Room(
                    name = "mock",
                    description = "mock room",
                    usersQuantity = 0,
                    id = 1,
                    owner = User(
                        username = "MockUser"
                    )
                ), rooms.rooms.first()
            )
        }
    }

    @Test
    fun `should receive Unauthorized if not authed when GET room id`() = baseTestApplication {
        val client = createClient {
            install(HttpCookies)
            install(ContentNegotiation) {
                json()
            }
        }
        client.get("/rooms/1").run {
            assertEquals(HttpStatusCode.Unauthorized, this.status)
        }
    }

    @Test
    fun `should receive OK if authed when GET room id`() = baseTestApplication {
        val client = createClient {
            install(HttpCookies)
            install(ContentNegotiation) {
                json()
            }
        }
        client.post("/login") {
            contentType(ContentType.Application.FormUrlEncoded)
            setBody("username=MockUser&password=123")
        }.also {
            assertEquals(HttpStatusCode.OK, it.status)
        }
        client.get("/rooms/1").run {
            assertEquals(HttpStatusCode.OK, this.status)
        }
    }

    @Test
    fun `should receive OK if authed when creating a new room`() = baseTestApplication {
        val client = createClient {
            install(HttpCookies)
            install(ContentNegotiation) {
                json()
            }
        }
        client.post("/login") {
            contentType(ContentType.Application.FormUrlEncoded)
            setBody("username=MockUser&password=123")
        }.also {
            assertEquals(HttpStatusCode.OK, it.status)
        }
        client.post("/rooms") {
            contentType(ContentType.Application.Json)
            setBody(RoomRequest("newroom", description = "brand new description"))
        }.run {
            assertEquals(HttpStatusCode.Created, this.status)
        }
    }

    @Test
    fun `should receive Unauthorized if not authed when creating a new room`() = baseTestApplication {
        val client = createClient {
            install(HttpCookies)
            install(ContentNegotiation) {
                json()
            }
        }

        client.post("/rooms") {
            contentType(ContentType.Application.Json)
            setBody(RoomRequest("newroom", description = "brand new description"))
        }.run {
            assertEquals(HttpStatusCode.Unauthorized, this.status)
        }
    }

    @Test
    fun `should not login if credentials are wrong`() = baseTestApplication {
        val client = createClient {
            install(HttpCookies)
            install(ContentNegotiation) {
                json()
            }
        }

        client.post("/login") {
            contentType(ContentType.Application.FormUrlEncoded)
            setBody("username=MockUser&password=wrong")
        }.run {
            assertEquals(HttpStatusCode.Unauthorized, this.status)
        }
    }

    @Test
    fun `should conflict when username already exists`() = baseTestApplication {
        val client = createClient {
            install(HttpCookies)
            install(ContentNegotiation) {
                json()
            }
        }

        client.post("/users") {
            contentType(ContentType.Application.Json)
            setBody(UserAccount("MockUser", "123"))
        }.run {
            assertEquals(HttpStatusCode.Conflict, this.status)
        }
    }

    private fun baseTestApplication(block: suspend ApplicationTestBuilder.() -> Unit) {
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
                configureSerialization()
            }
            block()
        }
    }

}
