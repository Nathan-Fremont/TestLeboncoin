package com.nfremont.albums.list.repository

import com.nfremont.albums.list.datasource.AlbumsListDataSource
import com.nfremont.albums.list.datasource.JsonAlbumsItem
import com.nfremont.albums.list.datasource.JsonAlbumsListResponse
import com.nfremont.core.CacheManager
import com.nfremont.core.JsonBaseResponse
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
class AlbumsListRepositoryTest {

    @MockK
    private lateinit var dataSource: AlbumsListDataSource

    private lateinit var albumsListRepository: AlbumsListRepository

    @BeforeEach
    fun setUp() {
        albumsListRepository = AlbumsListRepositoryImpl(
            dataSource,
            CacheManager()
        )
    }

    @AfterEach
    fun clean() {
        stopKoin()
    }

    @Test
    fun `getAlbumsList - when DataSource is JsonAlbumsListResponse_JsonAlbumsList - returns AlbumsListResponse_Success`() {
        runBlocking {
            // Given
            coEvery {
                dataSource.execute(
                    Unit
                )
            } returns JsonBaseResponse(
                response = JsonAlbumsListResponse.JsonAlbumsList(
                    albums = listOf(
                        JsonAlbumsItem(
                            albumId = 1,
                            id = 1,
                            title = "accusamus beatae ad facilis cum similique qui sunt",
                            url = "https://via.placeholder.com/600/92c952",
                            thumbnailUrl = "https://via.placeholder.com/150/92c952",
                        ),
                    )
                )
            )

            // When
            val result = albumsListRepository.getAlbumsList()

            // Then
            assertEquals(
                result,
                AlbumsListResponse.Success(
                    albums = listOf(
                        AlbumsItem(
                            albumId = 1,
                            id = 1,
                            title = "accusamus beatae ad facilis cum similique qui sunt",
                            url = "https://via.placeholder.com/600/92c952",
                            thumbnailUrl = "https://via.placeholder.com/150/92c952",
                        )
                    )
                ),
            )
        }
    }

    @Test
    fun `getAlbumsList - when DataSource is JsonAlbumsListResponse_Failure - returns Failure`() {
        runBlocking {
            // Given
            coEvery {
                dataSource.execute(
                    Unit
                )
            } returns JsonBaseResponse(
                response = JsonAlbumsListResponse.Failure
            )

            // When
            val result = albumsListRepository.getAlbumsList()

            // Then
            assertEquals(
                result,
                AlbumsListResponse.Failure
            )
        }
    }
}