package com.example.pafpaf.features.album_list

import androidx.compose.runtime.mutableStateListOf
import com.example.pafpaf.features.album_list.model.Album

data class AlbumListUiState(
    val albumList: List<Album> = emptyList(),
    val showAlbumDialog: Boolean = false,
    var showBottomMenu: Boolean = false,
    val selectedAlbums: MutableList<Album> = mutableStateListOf()
)