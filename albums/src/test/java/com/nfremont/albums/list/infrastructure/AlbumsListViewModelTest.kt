package com.nfremont.albums.list.infrastructure

import com.nfremont.albums.list.repository.AlbumsItem
import com.nfremont.albums.list.repository.AlbumsListRepository
import com.nfremont.albums.list.repository.AlbumsListResponse
import com.nfremont.coreTest.CoroutineTestExtension
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
class AlbumsListViewModelTest {
    @MockK
    private lateinit var repository: AlbumsListRepository

    private lateinit var albumsListViewModel: AlbumsListViewModel

    @BeforeEach
    fun setUp() {
        albumsListViewModel = AlbumsListViewModel(
            albumsListRepository = repository
        )
    }

    @Test
    fun `getAlbumsList - when repository is Success - update uiState to AlbumsListViewState_AlbumsListSuccess`() {
        runTest {
            // Given
            coEvery {
                repository.getAlbumsList()
            } returns AlbumsListResponse.Success(
                albums = listOf(
                    AlbumsItem(
                        albumId = 1,
                        id = 1,
                        title = "accusamus beatae ad facilis cum similique qui sunt",
                        url = "https://via.placeholder.com/600/92c952",
                        thumbnailUrl = "https://via.placeholder.com/150/92c952",
                    )
                )
            )

            // When
            albumsListViewModel.getAlbumsList()

            // Then
            assertEquals(
                expected = AlbumsListViewState.AlbumsListInProgress,
                actual = albumsListViewModel.uiState.value,
            )
            yield()
            coVerify {
                repository.getAlbumsList()
            }
            assertEquals(
                expected = AlbumsListViewState.AlbumsListSuccess(albums = listOf(
                    AlbumsItem(
                        albumId = 1,
                        id = 1,
                        title = "accusamus beatae ad facilis cum similique qui sunt",
                        url = "https://via.placeholder.com/600/92c952",
                        thumbnailUrl = "https://via.placeholder.com/150/92c952",
                    )
                )),
                actual = albumsListViewModel.uiState.value
            )
        }
    }

    @Test
    fun `getAlbumsList - when repository is Failure - update uiState to AlbumsListViewState_AlbumsListError`() {
        runTest {
            // Given
            coEvery {
                repository.getAlbumsList()
            } returns AlbumsListResponse.Failure

            // When
            albumsListViewModel.getAlbumsList()

            // Then
            assertEquals(
                expected = AlbumsListViewState.AlbumsListInProgress,
                actual = albumsListViewModel.uiState.value,
            )
            yield()
            coVerify {
                repository.getAlbumsList()
            }
            assertEquals(
                expected = AlbumsListViewState.AlbumsListError,
                actual = albumsListViewModel.uiState.value
            )
        }
    }

    @Test
    fun reset() {
        // Given
        albumsListViewModel._uiState.value = AlbumsListViewState.AlbumsListError

        // When
        albumsListViewModel.reset()

        // Then
        assertEquals(
            expected = AlbumsListViewState.AlbumsListDefault,
            actual = albumsListViewModel.uiState.value
        )
    }
}