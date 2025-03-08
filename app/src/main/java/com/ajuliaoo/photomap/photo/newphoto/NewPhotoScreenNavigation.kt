package com.ajuliaoo.photomap.photo.newphoto

import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ajuliaoo.photomap.NewPhoto

fun NavGraphBuilder.addNewPhotoRoute(
    onBack: () -> Unit,
    onSavePhoto: () -> Unit
) {
    composable<NewPhoto> {
        val newPhoto = it.toRoute<NewPhoto>()
        val viewModel = hiltViewModel<NewPhotoViewModel>()

        val context = LocalContext.current

        NewPhotoScreen(
            latitude = newPhoto.latitude,
            longitude = newPhoto.longitude,
            onBack = onBack,
            onSavePhoto = { uri ->
                viewModel.savePhoto(
                    context = context,
                    uri = uri,
                    latitude = newPhoto.latitude,
                    longitude = newPhoto.longitude
                )
                onSavePhoto()
            }
        )
    }
}