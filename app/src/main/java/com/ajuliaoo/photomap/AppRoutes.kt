package com.ajuliaoo.photomap

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object Tip

@Serializable
data class NewPhoto(
    val latitude: Double,
    val longitude: Double
)

@Serializable
data class Visualizer(
    val uuid: String
)