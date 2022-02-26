package com.nfremont.albums.list.infrastructure

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nfremont.albums.list.repository.AlbumsItem
import com.nfremont.albums.list.repository.AlbumsListRepository
import com.nfremont.albums.list.repository.AlbumsListResponse
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed class AlbumsListViewState {
    data class AlbumsListSuccess(val albums: List<AlbumsItem>) : AlbumsListViewState()
    object AlbumsListDefault : AlbumsListViewState()
    object AlbumsListInProgress : AlbumsListViewState()
    object AlbumsListError : AlbumsListViewState()
}

class AlbumsListViewModel(
    private val albumsListRepository: AlbumsListRepository,
) : ViewModel() {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val _uiState = MutableStateFlow<AlbumsListViewState>(AlbumsListViewState.AlbumsListDefault)
    val uiState: StateFlow<AlbumsListViewState> = _uiState.asStateFlow()
    private var fetchJob: Job? = null

    fun getAlbumsList() {
        fetchJob?.cancel()
        _uiState.value = AlbumsListViewState.AlbumsListInProgress
        fetchJob = viewModelScope.launch {
            try {
                val response = albumsListRepository.getAlbumsList()
                _uiState.value = when (response) {
                    AlbumsListResponse.Failure -> AlbumsListViewState.AlbumsListError
                    is AlbumsListResponse.Success -> AlbumsListViewState.AlbumsListSuccess(response.albums)
                }
            } catch (e: IOException) {
                _uiState.value = AlbumsListViewState.AlbumsListError
            }
        }
    }

    fun reset() {
        _uiState.value = AlbumsListViewState.AlbumsListDefault
    }
}