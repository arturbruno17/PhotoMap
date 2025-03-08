package com.ajuliaoo.photomap.photo.data

import com.ajuliaoo.photomap.photo.data.daos.PhotosDao
import com.ajuliaoo.photomap.photo.data.entities.PhotosEntity
import javax.inject.Inject

class PhotosRepository @Inject constructor(
    private val photosDao: PhotosDao
) {
    val photos = photosDao.getAll()

    suspend fun insert(photo: PhotosEntity) {
        photosDao.insert(photo)
    }

    suspend fun delete(uuid: String) {
        photosDao.delete(uuid)
    }
}
