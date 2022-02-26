package com.nfremont.albums.list.repository

import com.nfremont.albums.list.datasource.AlbumsListDataSource
import com.nfremont.albums.list.datasource.JsonAlbumsListResponse
import com.nfremont.core.CacheManager


class AlbumsListRepositoryImpl(
    dataSource: AlbumsListDataSource,
    cacheManager: CacheManager,
) : AlbumsListRepository(dataSource, cacheManager) {

    override suspend fun getAlbumsList(): AlbumsListResponse = transformToEntity(
        dataSource.execute(
            Unit
        ).response
    )

    override suspend fun transformToEntity(jsonResponse: JsonAlbumsListResponse): AlbumsListResponse =
        with(jsonResponse) {
            when (this) {
                is JsonAlbumsListResponse.JsonAlbumsList -> AlbumsListResponse.Success(
                    albums = albums.map { album ->
                        AlbumsItem(
                            albumId = album.albumId,
                            id = album.id,
                            title = album.title,
                            url = album.url,
                            thumbnailUrl = album.thumbnailUrl
                        )
                    }
                )
                JsonAlbumsListResponse.Failure -> AlbumsListResponse.Failure
            }
        }
}