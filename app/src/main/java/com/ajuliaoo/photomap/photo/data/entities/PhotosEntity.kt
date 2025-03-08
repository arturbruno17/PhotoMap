package com.ajuliaoo.photomap.photo.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class PhotosEntity(
    @PrimaryKey val uuid: String,
    @ColumnInfo("latitude") val latitude: Double,
    @ColumnInfo("longitude") val longitude: Double,
)
