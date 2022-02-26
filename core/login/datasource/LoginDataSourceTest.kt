package com.nfremont.mangareader.core.login.datasource

import com.nfremont.mangareader.core.*
import com.nfremont.mangareader.core.login.loginModule
import io.ktor.client.engine.mock.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals

class LoginDataSourceTest : KoinTest {

    private val loginDataSource: LoginDataSource by inject()

    @BeforeEach
    fun setUp() {
        startKoin {
            modules(
                objectMapperModule,
                loginModule,
            )
        }
    }

    @AfterEach
    fun clean() {
        stopKoin()
    }

    @Test
    fun `execute - when response is 200 - should get JsonLogin_Success`() {
        runBlocking {
            loginDataSource.client =
                getMockEngine {
                    respondWithFile200("auth_login_200.json")
                }

            // When
            val result = loginDataSource.execute(
                JsonLoginRequest(
                    username = "testUsername",
                    email = "test@test.com",
                    password = "1234567890"
                )
            )

            // Then
            assertEquals(
                result.response,
                JsonLoginResponse.Success(
                    token = JsonLoginResponse.JsonLoginToken(
                        session = "token_session",
                        refresh = "token_refresh"
                    )
                )
            )
        }
    }

    @Test
    fun `execute - when response is 400 - should get JsonLogin_BadRequest`() {
        runBlocking {
            loginDataSource.client =
                getMockEngine {
                    respondWithFile400("auth_login_400.json")
                }

            // When
            val result =
                loginDataSource.execute(JsonLoginRequest(username = "", email = "", password = ""))

            // Then
            assertEquals(
                result.response,
                JsonLoginResponse.BadRequest(
                    errors = listOf(
                        JsonLoginResponse.JsonBadRequestError(
                            id = "id1",
                            status = -545280,
                            title = "minim sunt eiusmod",
                            detail = "nulla enim",
                        ),
                        JsonLoginResponse.JsonBadRequestError(
                            id = "id2",
                            status = -8037053,
                            title = "adipisicing id labore ad",
                            detail = "in veniam enim nisi",
                        ),
                    )
                )
            )
        }
    }

    @Test
    fun `execute - when response is another response 401 - should get JsonLogin_Failure`() {
        runBlocking {
            loginDataSource.client =
                getMockEngine {
                    respondWithFile401("auth_login_400.json")
                }

            // When
            val result =
                loginDataSource.execute(JsonLoginRequest(username = "", email = "", password = ""))

            // Then
            assertEquals(result.response, JsonLoginResponse.Failure)
        }
    }

    @Test
    fun `execute - when response is empty - should get JsonLogin_Failure`() {
        runBlocking {
            loginDataSource.client =
                getMockEngine {
                    respond("")
                }

            // When
            val result =
                loginDataSource.execute(JsonLoginRequest(username = "", email = "", password = ""))

            // Then
            assertEquals(result.response, JsonLoginResponse.Failure)
        }
    }
}