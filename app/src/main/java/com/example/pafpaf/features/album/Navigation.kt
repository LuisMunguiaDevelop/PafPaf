package com.example.pafpaf.features.album

import com.example.pafpaf.features.album_list.model.Album
import kotlinx.serialization.Serializable

@Serializable
data class AlbumScreenNav(
    val id: Int,
    val title: String = ""
) {
    fun toAlbum(): Album {
        return Album(
            id = id,
            title = title
        )
    }
}