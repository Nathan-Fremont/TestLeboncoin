package com.nfremont.albums.list

import com.nfremont.albums.list.datasource.AlbumsListDataSource
import com.nfremont.albums.list.datasource.AlbumsListDataSourceImpl
import com.nfremont.albums.list.infrastructure.AlbumsListViewModel
import com.nfremont.albums.list.repository.AlbumsListRepository
import com.nfremont.albums.list.repository.AlbumsListRepositoryImpl
import com.nfremont.core.CacheManager
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val albumsListModule = module {
    single<AlbumsListDataSource> {
        AlbumsListDataSourceImpl()
    }
    single<AlbumsListRepository> {
        AlbumsListRepositoryImpl(dataSource = get(), cacheManager = CacheManager())
    }
    viewModel {
        AlbumsListViewModel(albumsListRepository = get())
    }
}