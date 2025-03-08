package com.ajuliaoo.photomap.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Looper
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.ajuliaoo.photomap.R
import com.ajuliaoo.photomap.home.components.PermissionBox
import com.ajuliaoo.photomap.home.components.PhotoGoogleMap
import com.ajuliaoo.photomap.photo.data.entities.PhotosEntity
import com.ajuliaoo.photomap.ui.theme.PhotoMapTheme
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MissingPermission")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    photos: List<PhotosEntity>,
    clickedAutoComplete: LatLng?,
    query: String,
    onQueryChange: (String) -> Unit,
    autoCompletePredictions: List<String>,
    onAutoCompletePredictionClick: (Int, String) -> Unit,
    resetClickedAutoComplete: () -> Unit,
    onShowTip: () -> Unit,
    onClickAdd: (LatLng) -> Unit,
    onMarkerClick: (String) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var shownDialog by rememberSaveable { mutableStateOf(false) }
    var currentDeviceLatLng by remember { mutableStateOf<LatLng?>(null) }
    val cameraPositionState = rememberCameraPositionState()
    var latLngClicked by remember { mutableStateOf<LatLng?>(null) }
    val requiredPermissions = remember {
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }
    var allPermissionsGranted by remember {
        mutableStateOf(
            requiredPermissions.all {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }
        )
    }
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(clickedAutoComplete) {
        clickedAutoComplete?.let {
            scope.launch {
                cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(it, 17f))
            }
            resetClickedAutoComplete()
        }
    }

    DisposableEffect(Unit) {
        if (!shownDialog) {
            onShowTip()
            shownDialog = true
        }

        onDispose {
            latLngClicked = null
        }
    }

    Scaffold {
        Box(
            modifier = modifier
                .padding(it)
                .consumeWindowInsets(it)
        ) {
            DockedSearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                inputField = {
                    SearchBarDefaults.InputField(
                        modifier = Modifier.fillMaxWidth(),
                        query = query,
                        onQueryChange = onQueryChange,
                        onSearch = {
                            if (autoCompletePredictions.isNotEmpty()) {
                                onAutoCompletePredictionClick(0, autoCompletePredictions[0])
                                expanded = false
                            }
                        },
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                        placeholder = {
                            Text(text = stringResource(R.string.search_here))
                        },
                        trailingIcon = {
                            AnimatedVisibility(expanded || query.isNotBlank()) {
                                IconButton(
                                    onClick = {
                                        expanded = false
                                        onQueryChange("")
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = null,
                                    )
                                }
                            }
                        }
                    )
                },
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                LazyColumn(
                    modifier = Modifier.height(240.dp)
                ) {
                    itemsIndexed(autoCompletePredictions) { index, query ->
                        Text(
                            modifier = Modifier
                                .clickable {
                                    onAutoCompletePredictionClick(index, query)
                                    expanded = !expanded
                                }
                                .padding(vertical = 16.dp, horizontal = 16.dp)
                                .fillMaxWidth(),
                            text = query
                        )
                        if (index < autoCompletePredictions.lastIndex)
                            HorizontalDivider(
//                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(.3f),
                                thickness = 1.dp
                            )
                    }
                }
            }

            PhotoGoogleMap(
                modifier = Modifier.matchParentSize(),
                cameraPositionState = cameraPositionState,
                allPermissionsGranted = allPermissionsGranted,
                onMapClick = {
                    latLngClicked = null
                    expanded = false
                },
                onMapLongClick = { latLng -> latLngClicked = latLng }
            ) {
                photos.forEach { photo ->
                    MarkerComposable(
                        LatLng(photo.latitude, photo.longitude),
                        anchor = Offset(0.5f, 0.5f),
                        state = MarkerState(position = LatLng(photo.latitude, photo.longitude)),
                        onClick = {
                            onMarkerClick(photo.uuid)
                            true
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = ImageVector.vectorResource(R.drawable.baseline_camera_alt_24),
                            tint = Color(0xFF6B4E39),
                            contentDescription = null
                        )
                    }
                }
                latLngClicked?.let { latLng ->
                    Marker(
                        state = MarkerState(position = latLng),
                        title = stringResource(R.string.new_photo),
                    )
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PermissionBox(
                    permissions = requiredPermissions
                ) {
                    LaunchedEffect(Unit) {
                        allPermissionsGranted = true
                    }

                    val locationClient =
                        remember { LocationServices.getFusedLocationProviderClient(context) }
                    val locationRequest = remember {
                        LocationRequest.Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY, 5_000)
                            .build()
                    }
                    val locationCallback: LocationCallback = remember {
                        object : LocationCallback() {
                            override fun onLocationResult(locationResult: LocationResult) {
                                val location = locationResult.locations.firstOrNull()
                                location?.let {
                                    currentDeviceLatLng = LatLng(it.latitude, it.longitude)
                                }
                            }
                        }
                    }

                    DisposableEffect(Unit) {
                        locationClient.requestLocationUpdates(
                            locationRequest,
                            locationCallback,
                            Looper.getMainLooper()
                        )

                        onDispose {
                            locationClient.removeLocationUpdates(locationCallback)
                        }
                    }

                    FloatingActionButton(
                        onClick = {
                            currentDeviceLatLng?.let { location ->
                                scope.launch {
                                    cameraPositionState.animate(
                                        CameraUpdateFactory.newLatLngZoom(
                                            LatLng(location.latitude, location.longitude), 17f
                                        )
                                    )
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = null
                        )
                    }
                }
                AnimatedVisibility(latLngClicked != null) {
                    FloatingActionButton(onClick = { onClickAdd(latLngClicked!!) }) {
                        Icon(
                            imageVector = Icons.Outlined.Add, contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    PhotoMapTheme {
        HomeScreen(
            photos = emptyList(),
            clickedAutoComplete = LatLng(0.0, 0.0),
            query = "",
            onQueryChange = {},
            autoCompletePredictions = emptyList(),
            onAutoCompletePredictionClick = { _, _ -> },
            onShowTip = {},
            onClickAdd = {},
            onMarkerClick = {},
            resetClickedAutoComplete = {},
        )
    }
}