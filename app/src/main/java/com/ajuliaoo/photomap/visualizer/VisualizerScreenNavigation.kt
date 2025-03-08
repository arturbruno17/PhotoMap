package com.ajuliaoo.photomap.visualizer

import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ajuliaoo.photomap.Visualizer

fun NavGraphBuilder.addVisualizerRoute(
    onBack: () -> Unit,
    onDelete: () -> Unit
) {
    composable<Visualizer> {
        val visualizer = it.toRoute<Visualizer>()
        val visualizerViewModel = hiltViewModel<VisualizerViewModel>()

        val context = LocalContext.current
        VisualizerScreen(
            uuid = visualizer.uuid,
            onBack = onBack,
            onDelete = {
                visualizerViewModel.deletePhoto(context, visualizer.uuid)
                onDelete()
            }
        )
    }
}