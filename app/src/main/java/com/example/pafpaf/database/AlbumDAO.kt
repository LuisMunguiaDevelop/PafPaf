package com.example.pafpaf.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.pafpaf.database.entities.AlbumEntity
import com.example.pafpaf.database.entities.AlbumWithPhoto
import com.example.pafpaf.database.entities.PhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDAO {
    @Query("SELECT * FROM albums")
    fun getAll(): Flow<List<AlbumEntity>>

    @Transaction
    @Query("SELECT * FROM albums")
    fun getAlbumWithPhotos() : Flow<List<AlbumWithPhoto>>

    @Query("SELECT * FROM albums WHERE id LIKE :albumId LIMIT 1")
    fun getAlbumById(albumId: Int): AlbumEntity

    @Query("SELECT * FROM photos WHERE albumId LIKE :albumId LIMIT 1")
    fun getAlbumCover(albumId: Int) : PhotoEntity

    @Insert
    fun insert(vararg album: AlbumEntity)

    @Delete
    fun delete(album: AlbumEntity)

    @Query("DELETE FROM albums WHERE id IN (:ids)")
    fun delete(ids: List<Int>)
}