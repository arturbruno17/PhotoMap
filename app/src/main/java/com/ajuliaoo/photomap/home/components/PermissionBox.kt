package com.ajuliaoo.photomap.home.components

import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun PermissionBox(
    permissions: List<String>,
    onGranted: @Composable BoxScope.() -> Unit,
) {
    val context = LocalContext.current

    var allPermissionsGranted by remember(permissions) {
        mutableStateOf(
            permissions.all {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }
        )
    }

    val permissionsLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { requestedPermissions ->
            allPermissionsGranted = requestedPermissions.filterValues { it }
                .keys.containsAll(permissions)
        }

    LaunchedEffect(Unit) {
        if (!allPermissionsGranted) {
            permissionsLauncher.launch(permissions.toTypedArray())
        }
    }

    AnimatedVisibility(allPermissionsGranted) {
        Box(content = onGranted)
    }
}