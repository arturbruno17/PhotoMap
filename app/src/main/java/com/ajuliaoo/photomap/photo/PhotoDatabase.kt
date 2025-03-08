package com.ajuliaoo.photomap.photo;

import androidx.room.Database;
import androidx.room.RoomDatabase

import com.ajuliaoo.photomap.photo.data.daos.PhotosDao;
import com.ajuliaoo.photomap.photo.data.entities.PhotosEntity

@Database(entities = [PhotosEntity::class], version = 1)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun photosDao(): PhotosDao
}
