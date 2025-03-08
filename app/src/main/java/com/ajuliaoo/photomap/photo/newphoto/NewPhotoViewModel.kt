package com.ajuliaoo.photomap.photo.newphoto

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajuliaoo.photomap.photo.data.PhotosRepository
import com.ajuliaoo.photomap.photo.data.entities.PhotosEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class NewPhotoViewModel @Inject constructor(
    private val photosRepository: PhotosRepository
) : ViewModel() {
    fun savePhoto(context: Context, uri: Uri, latitude: Double, longitude: Double) {
        val uuid = UUID.randomUUID().toString()

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    context.openFileOutput(uuid, Context.MODE_PRIVATE).use { outputStream ->
                        val buffer = ByteArray(4 * 1024)
                        var byteCount: Int
                        while (inputStream.read(buffer).also { byteCount = it } != -1) {
                            outputStream.write(buffer, 0, byteCount)
                        }
                        outputStream.flush()
                    }
                }
            }
            photosRepository.insert(PhotosEntity(uuid, latitude, longitude))
        }
    }
}