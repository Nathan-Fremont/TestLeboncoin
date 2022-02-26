package com.nfremont.testleboncoin

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.nfremont.albums.list.infrastructure.AlbumsListViewModel
import com.nfremont.albums.list.infrastructure.AlbumsListViewState
import com.nfremont.testleboncoin.ui.theme.TestLeboncoinTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val albumsListViewModel: AlbumsListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestLeboncoinTheme {
                val onCreateKey = rememberSaveable { mutableStateOf("") }
                val albumsListViewState = albumsListViewModel.uiState.collectAsState().value

                LaunchedEffect(key1 = onCreateKey, block = {
                    albumsListViewModel.getAlbumsList()
                })

                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainScreen(albumsListViewState = albumsListViewState)
                }
            }
        }
    }
}

@Composable
fun MainScreen(albumsListViewState: AlbumsListViewState) {
    Box(modifier = Modifier, contentAlignment = Alignment.Center) {
        when (albumsListViewState) {
            AlbumsListViewState.AlbumsListDefault -> {
                Text(text = stringResource(id = R.string.albums_list_default))
            }
            AlbumsListViewState.AlbumsListError -> {
                Text(text = stringResource(id = R.string.albums_list_error))
            }
            AlbumsListViewState.AlbumsListInProgress -> {
                CircularProgressIndicator()
            }
            is AlbumsListViewState.AlbumsListSuccess -> {
                AlbumsList(albums = albumsListViewState.albums)
            }
        }
    }
}

@Preview(name = "Light mode", showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "Dark mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    TestLeboncoinTheme {
        Surface() {
            Text("Android")
        }
    }
}