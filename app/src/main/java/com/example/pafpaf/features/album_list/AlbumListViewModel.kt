package com.example.pafpaf.features.album_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pafpaf.database.AlbumDAO
import com.example.pafpaf.database.entities.AlbumEntity
import com.example.pafpaf.features.album_list.model.Album
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumListViewModel @Inject constructor(
    private val albumDAO: AlbumDAO
) : ViewModel() {

    var state by mutableStateOf(AlbumListUiState())
        private set

    init {
        viewModelScope.launch {
            albumDAO.getAlbumWithPhotos().collectLatest { albumList ->

                state =
                    state.copy(albumList = albumList.map {
                        Album(
                            id = it.album.id,
                            title = it.album.title,
                            isSelected = false,
                            coverImage = if (it.photos.isNotEmpty()) {
                                it.photos.sortedBy { it.albumId }.last().path
                            } else {
                                ""
                            }
                        )
                    })

            }
        }


    }


    fun dispatchEvent(event: AlbumListEvent) {
        when (event) {
            is AlbumListEvent.AddAlbum -> addAlbum(event.album)
            is AlbumListEvent.DeleteAlbum -> deleteAlbums(event.albums)
        }
    }

    private fun addAlbum(album: Album) = viewModelScope.launch(Dispatchers.IO) {
        val albumEntity = AlbumEntity(id = album.id, title = album.title)
        albumDAO.insert(albumEntity)
    }

    private fun deleteAlbums(albums: List<Album>) = viewModelScope.launch(Dispatchers.IO) {
        albumDAO.delete(albums.map { it.id })
    }


}