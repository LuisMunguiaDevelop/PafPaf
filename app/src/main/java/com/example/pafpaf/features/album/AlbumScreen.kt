package com.example.pafpaf.features.album

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.pafpaf.R
import com.example.pafpaf.features.album.components.PhotoItem
import com.example.pafpaf.features.album.model.Photo
import com.example.pafpaf.features.album.usecase.PhotoClickEvents
import com.example.pafpaf.features.album.usecase.handlePhotoClick
import com.example.pafpaf.features.album_list.model.Album


@Composable
fun AlbumScreen(
    album: Album,
    viewModel: AlbumsViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    val uiState = viewModel.state

    viewModel.dispatchEvents(
        AlbumEvents.SetAlbum(album)
    )

    AlbumScreenContent(
        album = album,
        uiState = uiState,
        event = viewModel::dispatchEvents,
        onBackPressed = onBackPressed
    )
}


@Composable
fun AlbumScreenContent(
    album: Album,
    uiState: AlbumUiState,
    event: (AlbumEvents) -> Unit,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current

    val showPhoto = remember { mutableStateOf(false) }
    val photoSelected = remember { mutableStateOf("") }

    val isInSelectionMode = remember { mutableStateOf(false) }

    val selectedItems = remember { mutableStateListOf<Photo>() }

    val resetSelectionMode = {
        isInSelectionMode.value = false
        selectedItems.clear()
    }

    BackHandler(
        enabled = (isInSelectionMode.value || showPhoto.value),
    ) {
        resetSelectionMode()
    }

    LaunchedEffect(
        key1 = isInSelectionMode,
        key2 = selectedItems.size,
    ) {
        if (isInSelectionMode.value && selectedItems.isEmpty()) {
            isInSelectionMode.value = false
        }

        if (selectedItems.isNotEmpty()) {
            isInSelectionMode.value = true
        }
    }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            val bitmapsToAdd: MutableList<Bitmap> = mutableListOf()
            uris.onEach { uri ->
                val source = ImageDecoder.decodeBitmap(
                    ImageDecoder.createSource(
                        context.contentResolver,
                        uri
                    )
                )
                val photo: Bitmap = source
                bitmapsToAdd.add(photo)
            }
            event.invoke(AlbumEvents.AddPhotos(photos = bitmapsToAdd))
        }
    )

    //Content
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
            topBar = {
                TopBar(
                    title = album.title,
                    onBackPressed = { onBackPressed.invoke() }
                )
            },
            floatingActionButton = {
                if (!isInSelectionMode.value) {
                    FloatingActionButton(
                        onClick = {
                            singlePhotoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                    ) {
                        Icon(Icons.Filled.Add, "Floating action button.")
                    }
                }
            },
            bottomBar = {
                if (isInSelectionMode.value) {
                    AlbumBottomBar(
                        onDeletePressed = {
                            event.invoke(AlbumEvents.DeletePhotos(selectedItems.toList()))
                            isInSelectionMode.value = false
                        }
                    )
                }
            }
        ) { innerPadding ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                contentPadding = innerPadding
            ) {
                items(uiState.photoList) { photo ->
                    val isSelected = selectedItems.contains(photo)
                    PhotoItem(
                        photo = photo,
                        isInSelectionMode = isInSelectionMode.value,
                        isSelected = isSelected,
                        onClick = {
                            val action = handlePhotoClick(
                                photoIsSelected = isSelected,
                                isInSelectionMode = isInSelectionMode.value
                            )
                            when (action) {
                                PhotoClickEvents.SHOW_PHOTO -> {
                                    showPhoto.value = true
                                    photoSelected.value = photo.path
                                }

                                PhotoClickEvents.ADD_SELECTED_ITEM -> {
                                    selectedItems.add(photo)
                                }

                                PhotoClickEvents.REMOVE_SELECTED_ITEM -> {
                                    selectedItems.remove(photo)
                                }
                            }
                        },
                        onLongClick = {
                            when (isInSelectionMode.value) {
                                true -> {
                                    if (!isSelected) selectedItems.add(photo)
                                }

                                false -> {
                                    selectedItems.add(photo)
                                }
                            }
                        }
                    )
                }
            }
        }


        //PHOTO DIALOG
        if (showPhoto.value) {
            Dialog(
                onDismissRequest = { showPhoto.value = false },
                properties = DialogProperties(
                    usePlatformDefaultWidth = false
                )
            ) {
                Box(modifier = Modifier.background(Color.Black)) {
                    Image(
                        painter = rememberAsyncImagePainter(photoSelected.value),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
            //END IF
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onBackPressed: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title, textAlign = TextAlign.Center) },
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "back",
                modifier = Modifier
                    .clickable { onBackPressed.invoke() }
            )
        }
    )
}

@Composable
fun AlbumBottomBar(
    onDeletePressed: () -> Unit
) {
    BottomAppBar {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { onDeletePressed.invoke() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    contentDescription = "delete"
                )
                Text(text = "Eliminar")
            }
        }
    }
}


@Preview
@Composable
fun PreviewAlbumContent() {
    AlbumScreenContent(
        album = Album(id = 1, title = "titulo", isSelected = false),
        uiState = AlbumUiState(
            album = Album(title = "Album 1"),
            photoList = listOf(
                Photo(),
                Photo(),
                Photo(),
            )
        ),
        event = {},
        onBackPressed = {}
    )
}

@Preview
@Composable
fun PreviewBottomBar() {
    AlbumBottomBar(
        onDeletePressed = {},
    )
}



