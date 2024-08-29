package com.example.pafpaf.features.album

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pafpaf.database.PhotoDAO
import com.example.pafpaf.database.entities.PhotoEntity
import com.example.pafpaf.features.album.model.Photo
import com.example.pafpaf.features.album.usecase.saveBitmapToExternalStorage
import com.example.pafpaf.features.album_list.model.Album
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    private val photoDAO: PhotoDAO,
    @ApplicationContext private val context: Context
) : ViewModel() {

    var state by mutableStateOf(AlbumUiState())
        private set

    fun dispatchEvents(event: AlbumEvents) {
        when (event) {
            is AlbumEvents.SetAlbum -> setAlbum(event.album)
            is AlbumEvents.AddPhoto -> addPhoto(event.photo)
            is AlbumEvents.DeletePhotos -> deletePhotos(event.photos)
            is AlbumEvents.AddPhotos -> addPhotos(event.photos)
        }
    }

    private fun setAlbum(album: Album) {
        state = state.copy(album = album)
        fetchPhotos(album)
    }

    private fun fetchPhotos(album: Album) = viewModelScope.launch(Dispatchers.IO) {
        photoDAO.getAlbumPhotos(album.id).collect { photoList ->
            state = state.copy(
                photoList = photoList.map {
                    Photo(
                        id = it.id,
                        albumId = it.albumId,
                        path = it.path,
                        image = it.image
                    )
                }
            )
        }
    }

    private fun addPhotos(photos: List<Bitmap>) = viewModelScope.launch(Dispatchers.IO) {
        val photosToAdd : MutableList<PhotoEntity> = mutableListOf()
        photos.onEach { photo ->
            val path = saveBitmapToExternalStorage(
                bitmap = photo,
                context = context
            )
            if(path != null){
                photosToAdd.add(
                    PhotoEntity(
                        albumId = state.album.id,
                        path = path
                    )
                )
            }
        }

        photoDAO.insert(*photosToAdd.toTypedArray())
        fetchPhotos(state.album)
    }

    private fun addPhoto(photo: Bitmap) = viewModelScope.launch(Dispatchers.IO) {
        val path = saveBitmapToExternalStorage(
            bitmap = photo,
            context = context
        )

        if (path != null) {
            val photoEntity = PhotoEntity(
                albumId = state.album.id,
                path = path
            )
            photoDAO.insert(photoEntity)
            fetchPhotos(state.album)
        }
    }

    private fun deletePhotos(photos: List<Photo>) = viewModelScope.launch(Dispatchers.IO) {
        photos.onEach { photo ->
            val file = File(photo.path)
            try {
                file.delete() // Try to delete the file
            } catch (e: Exception) {
                // Handle the deletion error (e.g., log the error)
                e.printStackTrace()
            }
        }
        photoDAO.delete(ids = photos.map { it.id })
        //fetchPhotos()
    }

}