package com.ajuliaoo.photomap.home.components


import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.ajuliaoo.photomap.R
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings

@Composable
fun PhotoGoogleMap(
    modifier: Modifier = Modifier,
    allPermissionsGranted: Boolean,
    cameraPositionState: CameraPositionState,
    onMapClick: ((LatLng) -> Unit),
    onMapLongClick: ((LatLng) -> Unit),
    content: @Composable @GoogleMapComposable () -> Unit = {}
) {
    val context = LocalContext.current

    val mapUiSettings = remember {
        MapUiSettings(
            compassEnabled = false,
            mapToolbarEnabled = false,
            myLocationButtonEnabled = false,
            zoomControlsEnabled = false,
        )
    }

    val mapProperties = remember {
        MapProperties(
            isMyLocationEnabled = allPermissionsGranted
        )
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        uiSettings = mapUiSettings,
        properties = mapProperties,
        googleMapOptionsFactory = { GoogleMapOptions().mapId(context.getString(R.string.map_id)) },
        onMapClick = onMapClick,
        onMapLongClick = onMapLongClick,
        content = content
    )
}