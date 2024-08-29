package com.example.pafpaf.features.album

import com.example.pafpaf.features.album.model.Photo
import com.example.pafpaf.features.album_list.model.Album

data class AlbumUiState(
    val album: Album = Album(),
    val imageTest: String = "",
    val photoList: List<Photo> = emptyList()
)