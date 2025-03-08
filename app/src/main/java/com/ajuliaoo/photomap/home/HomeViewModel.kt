package com.ajuliaoo.photomap.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajuliaoo.photomap.photo.data.PhotosRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val placesClient: PlacesClient,
    photosRepository: PhotosRepository
) : ViewModel() {

    val photos = photosRepository.photos

    private val _query = MutableStateFlow("")
    val query: StateFlow<String>
        get() = _query

    private val _autoCompletePredictions = MutableStateFlow<List<AutocompletePrediction>>(emptyList())
    val autoCompletePredictions: StateFlow<List<AutocompletePrediction>>
        get() = _autoCompletePredictions

    private val _clickedAutoComplete = MutableStateFlow<LatLng?>(null)
    val clickedAutoComplete: StateFlow<LatLng?>
        get() = _clickedAutoComplete

    fun onQueryChange(query: String) {
        _query.value = query
        viewModelScope.launch {
            val autocompletePlacesRequest =
                FindAutocompletePredictionsRequest.builder()
                    .setQuery(query)
                    .build()
            _autoCompletePredictions.value = withContext(Dispatchers.IO) {
                placesClient.findAutocompletePredictions(autocompletePlacesRequest).await()
            }.autocompletePredictions
        }
    }

    fun onAutoCompletePredictionClick(index: Int, query: String) {
        viewModelScope.launch {
            val placeId = _autoCompletePredictions.value[index].placeId
            val placeFields: List<Place.Field> = listOf(Place.Field.LOCATION)
            val request = FetchPlaceRequest.newInstance(placeId, placeFields)
            val placeResponse = withContext(Dispatchers.IO) { placesClient.fetchPlace(request).await() }
            _clickedAutoComplete.value = placeResponse.place.location!!
            _query.value = query
        }
    }

    fun resetClickedAutoComplete() {
        _clickedAutoComplete.value = null
    }
}