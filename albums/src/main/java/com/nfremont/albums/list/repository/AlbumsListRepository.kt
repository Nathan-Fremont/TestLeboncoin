package com.nfremont.albums.list.repository

import com.nfremont.albums.list.datasource.AlbumsListDataSource
import com.nfremont.albums.list.datasource.JsonAlbumsListResponse
import com.nfremont.core.BaseRepository
import com.nfremont.core.CacheManager

abstract class AlbumsListRepository(
    protected val dataSource: AlbumsListDataSource,
    cacheManager: CacheManager,
) : BaseRepository<AlbumsListResponse, JsonAlbumsListResponse>(
    dataSource = dataSource,
    cacheManager = cacheManager,
) {
    abstract suspend fun getAlbumsList(): AlbumsListResponse
}