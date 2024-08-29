package com.example.pafpaf.features.album_list.model


data class Album(
    val id: Int = 0,
    val title: String = "",
    var isSelected : Boolean = false,
    var coverImage: String = ""
)