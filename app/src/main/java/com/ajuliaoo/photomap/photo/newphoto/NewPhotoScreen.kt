package com.ajuliaoo.photomap.photo.newphoto

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.ajuliaoo.photomap.R
import com.ajuliaoo.photomap.ui.theme.PhotoMapTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPhotoScreen(
    modifier: Modifier = Modifier,
    latitude: Double,
    longitude: Double,
    onBack: () -> Unit,
    onSavePhoto: (Uri) -> Unit,
) {
    val context = LocalContext.current
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
                title = {
                    Text(stringResource(R.string.new_photo))
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .consumeWindowInsets(it)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = latitude.toString(),
                onValueChange = {},
                label = {
                    Text(stringResource(R.string.latitude))
                },
                enabled = false,
                readOnly = true
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = longitude.toString(),
                onValueChange = {},
                label = {
                    Text(stringResource(R.string.longitude))
                },
                enabled = false,
                readOnly = true
            )
            Text(stringResource(R.string.cant_edit_latitude_and_longitude))

            var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
            val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                imageUri = uri
            }

            OutlinedButton(onClick = { pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_camera_alt_24),
                    contentDescription = null,
                )
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.add_photo))
            }
            if (imageUri != null) {
                AsyncImage(
                    modifier = Modifier.weight(1f),
                    model = ImageRequest.Builder(context)
                        .data(imageUri!!)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            } else {
                Spacer(Modifier.weight(1f))
            }
            Button(
                modifier = Modifier.align(Alignment.End),
                onClick = { if (imageUri != null) onSavePhoto(imageUri!!) }
            ) {
                Text(stringResource(R.string.create))
            }
        }
    }
}

@Preview
@Composable
private fun NewPhotoScreenPreview() {
    PhotoMapTheme {
        NewPhotoScreen(
            latitude = 0.0,
            longitude = 0.0,
            onBack = {}
        ) {}
    }
}