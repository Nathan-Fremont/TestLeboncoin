package com.nfremont.testleboncoin

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
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
                        modifier = Modifier
                            .size(128.dp)
                            .padding(all = 8.dp)
                    )

                    Text(text = album.title,
                        style = Typography.body1,
                        modifier = Modifier.padding(all = 8.dp))
                }
            }
        }
    })
}

@Composable
@Preview(name = "Dark mode", uiMode = UI_MODE_NIGHT_YES, widthDp = 100, heightDp = 100)
@Preview(name = "Light mode", uiMode = UI_MODE_NIGHT_NO, widthDp = 100, heightDp = 100)
fun AlbumsListPreview() {
    TestLeboncoinTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize()) {
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