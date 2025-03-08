package com.ajuliaoo.photomap.photo.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ajuliaoo.photomap.photo.data.entities.PhotosEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotosDao {

    @Query("SELECT * FROM photos")
    fun getAll(): Flow<List<PhotosEntity>>

    @Insert
    suspend fun insert(photo: PhotosEntity)

    @Query("DELETE FROM photos WHERE uuid = :uuid")
    suspend fun delete(uuid: String)

}
