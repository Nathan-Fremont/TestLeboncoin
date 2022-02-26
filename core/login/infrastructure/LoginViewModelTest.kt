package com.nfremont.mangareader.core.login.infrastructure

import com.nfremont.mangareader.core.CoroutineTestExtension
import com.nfremont.mangareader.core.login.repository.LoginRepository
import com.nfremont.mangareader.core.login.repository.LoginRequest
import com.nfremont.mangareader.core.login.repository.LoginResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.yield
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class, CoroutineTestExtension::class)
class LoginViewModelTest {
    @MockK
    private lateinit var repository: LoginRepository

    private lateinit var loginViewModel: LoginViewModel

    @BeforeEach
    fun setUp() {
        loginViewModel = LoginViewModel(
            loginRepository = repository
        )
    }

    @Test
    fun `login - when repository is Success - update uiState to LoginSuccess`() {
        runTest {
            // Given
            coEvery {
                repository.login(LoginRequest(
                    username = "",
                    email = "",
                    password = ""
                ))
            } returns LoginResponse.Success(
                session = "",
                refresh = ""
            )

            // When
            loginViewModel.login(
                username = "",
                email = "",
                password = ""
            )

            // Then
            assertEquals(
                expected = LoginViewState.LoginInProgress,
                actual = loginViewModel.uiState.value,
            )
            yield()
            coVerify {
                repository.login(LoginRequest(
                    username = "",
                    email = "",
                    password = ""
                ))
            }
            assertEquals(
                expected = LoginViewState.LoginSuccess,
                actual = loginViewModel.uiState.value
            )
        }
    }

    @Test
    fun `login - when repository is BadRequest - update uiState to LoginError`() {
        runTest {
            // Given
            coEvery {
                repository.login(LoginRequest(
                    username = "",
                    email = "",
                    password = ""
                ))
            } returns LoginResponse.BadRequest(emptyList())

            // When
            loginViewModel.login(
                username = "",
                email = "",
                password = ""
            )

            // Then
            assertEquals(
                expected = LoginViewState.LoginInProgress,
                actual = loginViewModel.uiState.value,
            )
            yield()
            coVerify {
                repository.login(LoginRequest(
                    username = "",
                    email = "",
                    password = ""
                ))
            }
            assertEquals(
                expected = LoginViewState.LoginError,
                actual = loginViewModel.uiState.value
            )
        }
    }

    @Test
    fun `login - when repository is Failure - update uiState to LoginError`() {
        runTest {
            // Given
            coEvery {
                repository.login(LoginRequest(
                    username = "",
                    email = "",
                    password = ""
                ))
            } returns LoginResponse.Failure

            // When
            loginViewModel.login(
                username = "",
                email = "",
                password = ""
            )

            // Then
            assertEquals(
                expected = LoginViewState.LoginInProgress,
                actual = loginViewModel.uiState.value,
            )
            yield()
            coVerify {
                repository.login(LoginRequest(
                    username = "",
                    email = "",
                    password = ""
                ))
            }
            assertEquals(
                expected = LoginViewState.LoginError,
                actual = loginViewModel.uiState.value
            )
        }
    }
}