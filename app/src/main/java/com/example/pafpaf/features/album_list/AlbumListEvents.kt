package com.example.pafpaf.features.album_list

import com.example.pafpaf.features.album_list.model.Album

sealed class AlbumListEvent{
    data class AddAlbum(val album: Album) : AlbumListEvent()
    data class DeleteAlbum(val albums: List<Album>) : AlbumListEvent()
}