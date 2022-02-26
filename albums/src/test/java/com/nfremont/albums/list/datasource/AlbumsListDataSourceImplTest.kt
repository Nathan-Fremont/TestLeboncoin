package com.nfremont.albums.list.datasource

import com.nfremont.albums.list.albumsListModule
import com.nfremont.core.objectMapperModule
import com.nfremont.coreTest.getMockEngine
import com.nfremont.coreTest.respondWithFile200
import com.nfremont.coreTest.respondWithFile400
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class AlbumsListDataSourceImplTest : KoinTest {

    private val albumsListDataSource: AlbumsListDataSource by inject()

    @BeforeEach
    fun setUp() {
        startKoin {
            modules(
                objectMapperModule,
                albumsListModule,
            )
        }
    }

    @AfterEach
    fun clean() {
        stopKoin()
    }

    @Test
    fun `execute - when response is 200 - should get JsonAlbumsListResponse_Success`() {
        runBlocking {
            albumsListDataSource.client =
                getMockEngine {
                    respondWithFile200("albums_list_200.json")
                }

            // When
            val result = albumsListDataSource.execute(Unit)

            // Then
            assertEquals(
                result.response,
                JsonAlbumsListResponse.JsonAlbumsList(
                    albums = listOf(
                        JsonAlbumsItem(
                            albumId = 1,
                            id = 1,
                            title = "accusamus beatae ad facilis cum similique qui sunt",
                            url = "https://via.placeholder.com/600/92c952",
                            thumbnailUrl = "https://via.placeholder.com/150/92c952",
                        ),
                        JsonAlbumsItem(
                            albumId = 1,
                            id = 2,
                            title = "reprehenderit est deserunt velit ipsam",
                            url = "https://via.placeholder.com/600/771796",
                            thumbnailUrl = "https://via.placeholder.com/150/771796",
                        ),
                        JsonAlbumsItem(
                            albumId = 1,
                            id = 3,
                            title = "officia porro iure quia iusto qui ipsa ut modi",
                            url = "https://via.placeholder.com/600/24f355",
                            thumbnailUrl = "https://via.placeholder.com/150/24f355",
                        ),
                    )
                )
            )
        }
    }

    @Test
    fun `execute - when response is not successful - should get JsonAlbumsListResponse_BadRequest`() {
        runBlocking {
            albumsListDataSource.client =
                getMockEngine {
                    respondWithFile400("albums_list_200.json")
                }

            // When
            val result =
                albumsListDataSource.execute(Unit)

            // Then
            assertEquals(
                result.response,
                JsonAlbumsListResponse.Failure
            )
        }
    }
}