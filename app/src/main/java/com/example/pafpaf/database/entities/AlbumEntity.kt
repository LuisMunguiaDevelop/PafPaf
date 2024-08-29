package com.example.pafpaf.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class AlbumEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val album: Int = 0,
    val title: String
)