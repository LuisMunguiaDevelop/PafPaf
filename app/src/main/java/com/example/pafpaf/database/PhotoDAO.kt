package com.example.pafpaf.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pafpaf.database.entities.PhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDAO {
    @Query("SELECT * FROM photos")
    fun getAll(): Flow<List<PhotoEntity>>

    @Query("SELECT * FROM photos WHERE albumId LIKE :albumId")
    fun getAlbumPhotos(albumId: Int): Flow<List<PhotoEntity>>

    @Insert
    fun insert(vararg photo: PhotoEntity)

    @Delete
    fun delete(photo: PhotoEntity)

    @Query("DELETE FROM photos WHERE id IN (:ids)")
    fun delete(ids: List<Int>)
}