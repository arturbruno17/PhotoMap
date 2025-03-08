package com.ajuliaoo.photomap.visualizer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.ajuliaoo.photomap.ui.theme.PhotoMapTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisualizerScreen(
    modifier: Modifier = Modifier,
    uuid: String,
    onBack: () -> Unit,
    onDelete: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                title = {},
                actions = {
                    IconButton(
                        onClick = onDelete
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .consumeWindowInsets(it)
        ) {
            val context = LocalContext.current
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(context)
                    .data("${context.filesDir}/$uuid")
                    .build(),
                contentScale = ContentScale.Fit,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun VisualizerScreenPreview() {
    PhotoMapTheme {
        VisualizerScreen(
            uuid = "https://picsum.photos/200/300",
            onBack = {}
        ) {}
    }
}