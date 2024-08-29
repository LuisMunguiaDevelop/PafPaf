package com.example.pafpaf.di

import android.content.Context
import com.example.pafpaf.database.AlbumDAO
import com.example.pafpaf.database.AppDatabaseNew
import com.example.pafpaf.database.PhotoDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule{

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabaseNew {
        return AppDatabaseNew.getDatabase(appContext)
    }

    @Provides
    fun provideAlbumDao(appDatabase: AppDatabaseNew): AlbumDAO {
        return appDatabase.albumDao()
    }

    @Provides
    fun providePhotoDao(appDatabase: AppDatabaseNew): PhotoDAO {
        return appDatabase.photoDao()
    }
}