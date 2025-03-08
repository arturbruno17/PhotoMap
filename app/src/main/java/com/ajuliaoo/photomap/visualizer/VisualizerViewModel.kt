package com.ajuliaoo.photomap.visualizer

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajuliaoo.photomap.photo.data.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VisualizerViewModel @Inject constructor(
    private val photosRepository: PhotosRepository
) : ViewModel() {
    fun deletePhoto(context: Context, uuid: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { context.deleteFile(uuid) }
            photosRepository.delete(uuid)
        }
    }
}