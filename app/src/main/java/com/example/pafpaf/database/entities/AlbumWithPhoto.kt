package com.example.pafpaf.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class AlbumWithPhoto(
    @Embedded
    val album: AlbumEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "albumId"
    )
    val photos: List<PhotoEntity>
    )