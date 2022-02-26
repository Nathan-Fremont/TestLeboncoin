package com.nfremont.albums.list

import com.nfremont.albums.list.datasource.AlbumsListDataSource
import com.nfremont.albums.list.datasource.AlbumsListDataSourceImpl
import org.koin.dsl.module

val albumsListModule = module {
    single<AlbumsListDataSource> {
        AlbumsListDataSourceImpl()
    }
}