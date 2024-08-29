package com.example.pafpaf.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val albumId: Int = 0,
    val path: String = "",
    val image: String = ""
)