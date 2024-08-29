package com.example.pafpaf.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pafpaf.database.entities.AlbumEntity
import com.example.pafpaf.database.entities.PhotoEntity

@Database(
    entities = [AlbumEntity::class, PhotoEntity::class],
    version = 2,
    autoMigrations = [AutoMigration(from = 1, to = 2)],
    exportSchema = true
)
abstract class AppDatabaseNew : RoomDatabase() {
    abstract fun albumDao(): AlbumDAO
    abstract fun photoDao(): PhotoDAO

    companion object {
        private const val DATABASE_NAME = "databasenew.db"

        @Volatile
        private var Instance: AppDatabaseNew? = null


        fun getDatabase(context: Context): AppDatabaseNew {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabaseNew::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
