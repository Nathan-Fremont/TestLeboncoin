package com.nfremont.testleboncoin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.nfremont.albums.list.repository.AlbumsItem
import com.nfremont.testleboncoin.ui.theme.TestLeboncoinTheme
import com.nfremont.testleboncoin.ui.theme.Typography

@Composable
fun AlbumsList(albums: List<AlbumsItem>) {
    LazyColumn(content = {
        itemsIndexed(albums) { index: Int, album: AlbumsItem ->
            Column {
                if (index != 0) {
                    Divider()
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = rememberImagePainter(
                            data = album.url,
                            builder = {
                                transformations(CircleCropTransformation())
                            }
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(128.dp)
                    )

                    Text(text = album.title, style = Typography.body1)

                }
            }
        }
    })
}

@Composable
@Preview
fun AlbumsListPreview() {
    TestLeboncoinTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            AlbumsList(albums = listOf(
                AlbumsItem(
                    albumId = 1,
                    id = 1,
                    title = "accusamus beatae ad facilis cum similique qui sunt",
                    url = "https://via.placeholder.com/600/92c952",
                    thumbnailUrl = "https://via.placeholder.com/150/92c952",
                ),
                AlbumsItem(
                    albumId = 1,
                    id = 2,
                    title = "reprehenderit est deserunt velit ipsam",
                    url = "https://via.placeholder.com/600/771796",
                    thumbnailUrl = "https://via.placeholder.com/150/771796",
                ),
                AlbumsItem(
                    albumId = 1,
                    id = 3,
                    title = "officia porro iure quia iusto qui ipsa ut modi",
                    url = "https://via.placeholder.com/600/24f355",
                    thumbnailUrl = "https://via.placeholder.com/150/24f355",
                ),
            ))
        }
    }
}