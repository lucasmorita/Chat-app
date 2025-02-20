package endpoints

import dev.lmorita.db.H2ConnFactory
import dev.lmorita.db.RoomTable
import dev.lmorita.db.SessionTable
import dev.lmorita.db.UserAccountTable
import dev.lmorita.entities.RoomEntity
import dev.lmorita.entities.UserAccountEntity
import fixtures.LOGIN_URL
import fixtures.LOGOUT_URL
import fixtures.baseTestApplication
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.test.KoinTest
import org.mindrot.jbcrypt.BCrypt
import kotlin.test.Test
import kotlin.test.assertEquals

class AuthRoutingTest : KoinTest {

    private val database = Database.connect(H2ConnFactory.hikari())

    init {
        transaction(database) {
            SchemaUtils.create(UserAccountTable)
            SchemaUtils.create(RoomTable)
            SchemaUtils.create(SessionTable)
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
    fun `should logout when user is authed`() = baseTestApplication {
        val client = createClient {
            install(HttpCookies)
            install(ContentNegotiation) {
                json()
            }
        }
        client.post(LOGIN_URL) {
            contentType(ContentType.Application.FormUrlEncoded)
            setBody("username=MockUser&password=123")
        }

        client.delete(LOGOUT_URL).run {
            assertEquals(HttpStatusCode.OK, this.status)
        }
    }

    @Test
    fun `should get 401 if user is not authed when logging out`() = baseTestApplication {
        val client = createClient {
            install(HttpCookies)
            install(ContentNegotiation) {
                json()
            }
        }

        client.delete(LOGOUT_URL).run {
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

        client.post(LOGIN_URL) {
            contentType(ContentType.Application.FormUrlEncoded)
            setBody("username=MockUser&password=wrong")
        }.run {
            assertEquals(HttpStatusCode.Unauthorized, this.status)
        }
    }

}