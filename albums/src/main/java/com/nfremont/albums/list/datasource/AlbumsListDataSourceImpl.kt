package com.nfremont.albums.list.datasource

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.nfremont.core.JsonBaseResponse
import io.ktor.client.features.*

class AlbumsListDataSourceImpl : AlbumsListDataSource() {
    override fun getDataSourceName(): String = "AlbumsListDataSource"

    override suspend fun execute(request: Unit): JsonBaseResponse<JsonAlbumsListResponse> {
        try {
            val response = doGet<List<JsonAlbumsItem>>()

            return when (response.httpResponse?.status?.value) {
                in (200 until 300) -> {
                    JsonBaseResponse(
                        response = JsonAlbumsListResponse.JsonAlbumsList(
                            albums = response.response
                        ),
                    )
                }
                else -> {
                    JsonBaseResponse(response = JsonAlbumsListResponse.Failure)
                }
            }
        } catch (e: ResponseException) {
            e.printStackTrace()
        } catch (e: MismatchedInputException) {
            e.printStackTrace()
        }
        return JsonBaseResponse(response = JsonAlbumsListResponse.Failure)
    }
}