package com.ajuliaoo.photomap

import android.content.Context
import androidx.room.Room
import com.ajuliaoo.photomap.photo.PhotoDatabase
import com.ajuliaoo.photomap.photo.data.daos.PhotosDao
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PhotoMapDI {

    @Provides
    @Singleton
    fun providesPhotoDatabase(
        @ApplicationContext context: Context
    ): PhotoDatabase {
        return Room.databaseBuilder(
            context,
            PhotoDatabase::class.java,
            "photo_database.db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesPhotosDao(
        photoDatabase: PhotoDatabase
    ): PhotosDao {
        return photoDatabase.photosDao()
    }

    @Provides
    @Singleton
    fun providesPlacesClient(
        @ApplicationContext context: Context
    ): PlacesClient {
        return Places.createClient(context)
    }

}