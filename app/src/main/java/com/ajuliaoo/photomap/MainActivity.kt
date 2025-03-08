package com.ajuliaoo.photomap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ajuliaoo.photomap.home.addHomeScreenRoute
import com.ajuliaoo.photomap.photo.newphoto.addNewPhotoRoute
import com.ajuliaoo.photomap.tip.addTipDialogRoute
import com.ajuliaoo.photomap.ui.theme.PhotoMapTheme
import com.ajuliaoo.photomap.visualizer.addVisualizerRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PhotoMapTheme {
                val navController = rememberNavController()

                NavHost(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController,
                    startDestination = Home
                ) {
                    addHomeScreenRoute(
                        onClickAdd = {
                            navController.navigate(
                                NewPhoto(
                                    it.latitude,
                                    it.longitude
                                )
                            )
                        },
                        onShowTip = { navController.navigate(Tip) },
                        onClickMarker = { navController.navigate(Visualizer(it)) }
                    )
                    addTipDialogRoute { navController.popBackStack() }
                    addNewPhotoRoute(
                        onBack = { navController.popBackStack() },
                        onSavePhoto = { navController.popBackStack() }
                    )
                    addVisualizerRoute(
                        onBack = { navController.popBackStack() },
                        onDelete = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}