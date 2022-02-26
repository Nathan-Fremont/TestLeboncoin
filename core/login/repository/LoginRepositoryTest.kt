package com.nfremont.mangareader.core.login.repository

import com.nfremont.mangareader.core.CacheManager
import com.nfremont.mangareader.core.JsonBaseResponse
import com.nfremont.mangareader.core.login.datasource.JsonLoginRequest
import com.nfremont.mangareader.core.login.datasource.JsonLoginResponse
import com.nfremont.mangareader.core.login.datasource.LoginDataSource
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.core.context.stopKoin
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class LoginRepositoryTest {

    @MockK
    private lateinit var dataSource: LoginDataSource

    private lateinit var loginRepository: LoginRepository

    @BeforeEach
    fun setUp() {
        loginRepository = LoginRepositoryImpl(
            dataSource,
            CacheManager()
        )
    }

    @AfterEach
    fun clean() {
        stopKoin()
    }

    @Test
    fun `login - when DataSource is JsonLoginSuccess - returns LoginSuccess`() {
        runBlocking {
            // Given
            coEvery {
                dataSource.execute(
                    JsonLoginRequest(
                        username = "",
                        email = "",
                        password = "",
                    )
                )
            } returns JsonBaseResponse(
                response = JsonLoginResponse.Success(
                    token = JsonLoginResponse.JsonLoginToken(
                        session = "sessionTest",
                        refresh = "refreshTest",
                    )
                )
            )

            // When
            val result = loginRepository.login(
                LoginRequest(
                    username = "",
                    email = "",
                    password = "",
                )
            )

            // Then
            assertEquals(
                result,
                LoginResponse.Success(
                    session = "sessionTest",
                    refresh = "refreshTest",
                )
            )
        }
    }

    @Test
    fun `login - when DataSource is JsonLoginBadRequest - returns Failure`() {
        runBlocking {
            // Given
            coEvery {
                dataSource.execute(
                    JsonLoginRequest(
                        username = "",
                        email = "",
                        password = ""
                    )
                )
            } returns JsonBaseResponse(
                response = JsonLoginResponse.BadRequest(listOf())
            )

            // When
            val result = loginRepository.login(
                LoginRequest(
                    username = "",
                    email = "",
                    password = ""
                )
            )

            // Then
            assertEquals(
                result,
                LoginResponse.Failure
            )
        }
    }

    @Test
    fun `login - when DataSource is JsonLoginFailure - returns LoginFailure`() {
        runBlocking {
            // Given
            coEvery {
                dataSource.execute(
                    JsonLoginRequest(
                        username = "",
                        email = "",
                        password = ""
                    )
                )
            } returns JsonBaseResponse(
                response = JsonLoginResponse.Failure
            )

            // When
            val result = loginRepository.login(
                LoginRequest(
                    username = "",
                    email = "",
                    password = ""
                )
            )

            // Then
            assertEquals(
                result,
                LoginResponse.Failure
            )
        }
    }
}