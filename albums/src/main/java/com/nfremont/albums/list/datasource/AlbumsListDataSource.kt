package com.nfremont.albums.list.datasource

import com.nfremont.core.BaseDataSource
import com.nfremont.core.DataSourceServices

abstract class AlbumsListDataSource : BaseDataSource<Unit, JsonAlbumsListResponse>(
    serviceUrl = DataSourceServices.ALBUMS.url
) {
    override fun getDataSourceName(): String = "AlbumsListDataSource"
}