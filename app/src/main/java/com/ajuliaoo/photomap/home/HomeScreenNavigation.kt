package com.ajuliaoo.photomap.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ajuliaoo.photomap.Home
import com.google.android.gms.maps.model.LatLng
import androidx.core.text.toHtml

fun NavGraphBuilder.addHomeScreenRoute(
    onShowTip: () -> Unit,
    onClickAdd: (LatLng) -> Unit,
    onClickMarker: (String) -> Unit
) {
    composable<Home> {
        val context = LocalContext.current
        val homeViewModel = hiltViewModel<HomeViewModel>()

        val query by homeViewModel.query.collectAsStateWithLifecycle()
        val photos by homeViewModel.photos.collectAsStateWithLifecycle(emptyList())
        val clickedAutoComplete by homeViewModel.clickedAutoComplete.collectAsStateWithLifecycle()
        val autoCompletePredictions by homeViewModel.autoCompletePredictions.collectAsStateWithLifecycle()

        HomeScreen(
            modifier = Modifier.fillMaxSize(),
            photos = photos,
            query = query,
            onQueryChange = { homeViewModel.onQueryChange(it) },
            clickedAutoComplete = clickedAutoComplete,
            autoCompletePredictions = autoCompletePredictions.map {
                AnnotatedString.fromHtml(
                    htmlString = it.getFullText(null).toHtml()
                ).text.trim()
            },
            onAutoCompletePredictionClick = { index, value ->
                homeViewModel.onAutoCompletePredictionClick(
                    index = index,
                    query = value
                )
            },
            resetClickedAutoComplete = { homeViewModel.resetClickedAutoComplete() },
            onShowTip = onShowTip,
            onClickAdd = onClickAdd,
            onMarkerClick = onClickMarker,
        )
    }
}