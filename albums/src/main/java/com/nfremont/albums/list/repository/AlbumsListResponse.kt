package com.nfremont.albums.list.repository

sealed class AlbumsListResponse {

    data class Success(
        val albums: List<AlbumsItem>
    ) : AlbumsListResponse()

    object Failure : AlbumsListResponse()
}

data class AlbumsItem(
    val albumId: Int,
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String,
)