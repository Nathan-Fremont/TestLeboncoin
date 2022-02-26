package com.nfremont.albums.list.datasource

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
sealed class JsonAlbumsListResponse {
    data class JsonAlbumsList(val albums: List<JsonAlbumsItem>) : JsonAlbumsListResponse()
    object Failure: JsonAlbumsListResponse()
}

@Keep
data class JsonAlbumsItem(
    @JsonProperty("albumId")
    val albumId: Int,
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("url")
    val url: String,
    @JsonProperty("thumbnailUrl")
    val thumbnailUrl: String,
)