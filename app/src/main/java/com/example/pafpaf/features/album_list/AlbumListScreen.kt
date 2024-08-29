package com.example.pafpaf.features.album_list

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.pafpaf.R
import com.example.pafpaf.features.album_list.components.AddAlbumDialog
import com.example.pafpaf.features.album_list.components.AlbumListBottomMenu
import com.example.pafpaf.features.album_list.model.Album
import kotlinx.serialization.Serializable


@Serializable
object AlbumListScreenNav

@Composable
fun AlbumListScreen(
    viewModel: AlbumListViewModel = hiltViewModel(),
    onAlbumClicked: (Album) -> Unit,
    onBackclicked: () -> Unit,
) {
    val uiState = viewModel.state

    AlbumListContent(
        uiState = uiState,
        event = viewModel::dispatchEvent,
        onAlbumClicked = { album -> onAlbumClicked.invoke(album) },
        onBackclicked = { onBackclicked.invoke() }
    )
}

@Composable
fun AlbumListContent(
    uiState: AlbumListUiState,
    event: (AlbumListEvent) -> Unit,
    onAlbumClicked: (Album) -> Unit,
    onBackclicked: () -> Unit
) {

    val showAddAlbumDialog = remember { mutableStateOf(uiState.showAlbumDialog) }
    if (showAddAlbumDialog.value) {
        AddAlbumDialog(shouldShowDialog = showAddAlbumDialog, onConfirm = {
            event.invoke(
                AlbumListEvent.AddAlbum(Album(title = it))
            )
        })
    }


    val isInSelectionMode = remember { mutableStateOf(false) }
    val selectedItems = remember {
        mutableStateListOf<Album>()
    }

    val resetSelectionMode = {
        isInSelectionMode.value = false
        selectedItems.clear()
    }

    BackHandler {
        if(isInSelectionMode.value) resetSelectionMode() else onBackclicked.invoke()
    }

    LaunchedEffect(
        key1 = isInSelectionMode,
        key2 = selectedItems.size,
    ) {
        if (isInSelectionMode.value && selectedItems.isEmpty()) {
            isInSelectionMode.value = false
        }
    }


    Scaffold(
        floatingActionButton = {
            if (!isInSelectionMode.value) {
                AddAlbumButton(
                    onClick = { showAddAlbumDialog.value = true }
                )
            }
        },
        bottomBar = {
            if (isInSelectionMode.value) {
                AlbumListBottomMenu(
                    onDeleteAction = {
                        event.invoke(AlbumListEvent.DeleteAlbum(selectedItems.toList()))
                        resetSelectionMode()
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Column {

                //Top bar
                if (isInSelectionMode.value) {
                    IconButton(onClick = { resetSelectionMode() }) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_close_24),
                            contentDescription = "delete"
                        )
                    }
                }

                //Albums Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                ) {
                    items(uiState.albumList) { album ->
                        val isSelected = selectedItems.contains(album)
                        AlbumItem(
                            album = album,
                            isInSelectionMode = isInSelectionMode,
                            isSelected = isSelected,
                            onClick = {
                                when (isInSelectionMode.value) {
                                    true -> {
                                        if (isSelected) {
                                            selectedItems.remove(album)
                                        } else {
                                            selectedItems.add(album)
                                        }
                                    }

                                    false -> {
                                        onAlbumClicked.invoke(album)
                                    }
                                }
                            },
                            onLongClick = {
                                when (isInSelectionMode.value) {
                                    true -> {
                                        when (isSelected) {
                                            true -> {
                                                selectedItems.remove(album)
                                            }

                                            false -> {
                                                selectedItems.add(album)
                                            }
                                        }
                                    }

                                    false -> {
                                        isInSelectionMode.value = true
                                        selectedItems.add(album)
                                    }
                                }
                            }
                        )
                    }
                }
            }


        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlbumItem(
    album: Album,
    isInSelectionMode: MutableState<Boolean>,
    isSelected: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(20.dp)
            .combinedClickable(
                onClick = {
                    onClick.invoke()
                },
                onLongClick = {
                    onLongClick.invoke()
                }
            )
    ) {
        Box {

            if (album.coverImage.isNotBlank()) {
                AsyncImage(
                    model = album.coverImage,
                    modifier = Modifier
                        .fillMaxSize()
                        .size(150.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentDescription = "Saved Image",
                    contentScale = ContentScale.Crop
                )
            }

            if (album.coverImage.isBlank()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(20.dp))
                )
            }
            if (isInSelectionMode.value) {
                val selectedImg =
                    if (isSelected)
                        R.drawable.baseline_check_circle_24
                    else
                        R.drawable.baseline_radio_button_unchecked_24
                Image(
                    painter = painterResource(selectedImg),
                    contentDescription = "checked",
                    modifier = Modifier
                        .align(alignment = Alignment.BottomEnd)
                        .padding(5.dp),
                    colorFilter = ColorFilter.tint(Color.Cyan)
                )
            }
        }

        Text(text = album.title, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun AddAlbumButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = {
            onClick.invoke()
        },
    ) {
        Icon(Icons.Filled.Add, "Floating action button.")
    }
}


/////////////////////////////////

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun AlbumListScreenPreview() {
    AlbumListContent(
        uiState =
        AlbumListUiState(
            albumList = listOf(
                Album(id = 1, title = "angie"),
                Album(id = 1, title = "ea"),
                Album(id = 1, title = "ea")
            )
        ),
        event = {},
        onAlbumClicked = {},
        onBackclicked = {}
    )
}