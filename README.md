<h1 align="center">PhotoMap</h1>
<p align="center">Application to relate your photos with their locations.</p>

<p align="center">
   <img src="https://img.shields.io/static/v1?label=jetpack-compose-bom&message=2024.04.01&color=0A3041&style=flat-square"/>
   <img src="https://img.shields.io/static/v1?label=maps-sdk&message=19.1.0&color=1B72E8&style=flat-square"/>
   <img src="https://img.shields.io/static/v1?label=places-api&message=4.1.0&color=33A852&style=flat-square"/>
   <img src="https://img.shields.io/static/v1?label=coil&message=3.1.0&color=45DFF4&style=flat-square"/>
   <img src="https://img.shields.io/static/v1?label=hilt&message=2.55&color=2196F3&style=flat-square"/>
  <img src="https://img.shields.io/static/v1?label=room&message=2.6.1&color=33A852&style=flat-square"/>
</p>

<p align="center">
 <a href="#-about">About</a> •
 <a href="#-screenshots">Screenshots</a> • 
 <a href="#-bibliography">Bibliography</a>
</p>

# 📜 About

This application allows users to relate their photos with their respective locations on the map, providing an intuitive and interactive way to visualize memories tied to geographic coordinates.

### Features

**Location Search with Autocomplete**: Easily search for places with autocomplete suggestions. Upon selecting a suggestion, the map navigates to the desired location, similar to Google Maps.

**Current Location Navigation**: A button that takes the user directly to their current location on the map.

**Photo Markers**: Long-pressing on the map adds a marker with a button displaying a "+" symbol. Clicking this button opens a form containing the marker's latitude and longitude, along with an option to upload a photo associated with that location. Once submitted, the marker becomes fixed on the map.

**Photo Display**: Clicking on the marker reveals the associated photo.

**Marker Deletion**: The photo display screen includes an option to delete the marker, which removes both the photo from the app's internal storage and its reference in the database.

This app provides a seamless way to map memories with visual context, combining geolocation with photo management.

# 📱 Screenshots

<p align="center">
  <img src="https://i.ibb.co/d4syXcrx/Screenshot-20250305-121831.png" width="30%"/>
  <img src="https://i.ibb.co/hxpzHBPQ/Screenshot-20250305-121820.png" width="30%"/>
  <img src="https://i.ibb.co/xtTc9jtg/Screenshot-20250305-121836.png" width="30%"/>
</p>

# 📚 Bibliography

In this section, you'll find some links and resources which explain about what libraries are used in the project.

|  Biblioteca   |  Link    |
|---	|---	|
|   Jetpack Compose    |   https://developer.android.com/compose    |   
|   kotlinx-serialization    |   https://github.com/Kotlin/kotlinx.serialization    |
| Coil | https://github.com/coil-kt/coil |
| Coroutines | https://github.com/Kotlin/kotlinx.coroutines |
|   Hilt    |   https://developer.android.com/training/dependency-injection/hilt-android?hl=pt-br    |
| Maps SDK | https://developers.google.com/maps/documentation/android-sdk/overview?hl=pt-br |
| Places API | https://developers.google.com/maps/documentation/places/web-service?hl=pt-br |
| KSP | https://github.com/google/ksp |
| Room | https://developer.android.com/training/data-storage/room?hl=pt-br |