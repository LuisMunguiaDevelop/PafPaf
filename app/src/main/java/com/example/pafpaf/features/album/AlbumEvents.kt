package com.example.pafpaf.features.album

import android.graphics.Bitmap
import com.example.pafpaf.features.album.model.Photo
import com.example.pafpaf.features.album_list.model.Album

sealed class AlbumEvents{
    data class SetAlbum(val album: Album) : AlbumEvents()
    data class AddPhoto(val photo: Bitmap): AlbumEvents()
    data class DeletePhotos(val photos: List<Photo>): AlbumEvents()
    data class AddPhotos(val photos: List<Bitmap>) : AlbumEvents()
}