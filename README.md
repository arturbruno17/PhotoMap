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

This application allows users to relate their photos with their respective locations on the map,
providing an intuitive and interactive way to visualize memories tied to geographic coordinates.

## Features

**Location Search with Autocomplete**: Easily search for places with autocomplete suggestions. Upon
selecting a suggestion, the map navigates to the desired location, similar to Google Maps.

**Current Location Navigation**: A button that takes the user directly to their current location on
the map.

**Photo Markers**: Long-pressing on the map adds a marker with a button displaying a "+" symbol.
Clicking this button opens a form containing the marker's latitude and longitude, along with an
option to upload a photo associated with that location. Once submitted, the marker becomes fixed on
the map.

**Photo Display**: Clicking on the marker reveals the associated photo.

**Marker Deletion**: The photo display screen includes an option to delete the marker, which removes
both the photo from the app's internal storage and its reference in the database.

This app provides a seamless way to map memories with visual context, combining geolocation with
photo management.

## Architecture

The MVVM was chosen as the project architecture because it is the architecture recommended for use
in Android projects by Google. For a better understanding of MVVM, see the image below:

<p align="center">
    <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQVN0RK4gH17vEeYGhmQdifZnOksqteWzU8qA&s"/>
</p>

There're 3 layers in this architecture:

- **View:** The responsibility of the view layer is to define the user interface. This layer cannot
  handle business logic and should only receive the UI models and show them to the user.
- **ViewModel:** The ViewModel should be responsible for the business logic. This layer cannot know
  about which data sources it will use.
- **Model:** The model encapsulates the models and decides from which data sources the data will be
  retrieved through repositories.

> 💡 **Important to know**
> MVVM architecture follows a unidirectional data flow. Thus, the view layer cannot know about the
> viewmodel layer, just as the viewmodel layer cannot know about the data layer.

## Files and Folders

By default, Android projects have a common file structure. Therefore, I will only explain the files
that I actually modified.

**AndroidManifest.xml**: The manifest file describes essential information about your app to the
Android build tools, the Android operating system, and Google Play. The following tags are included
or modified manually:

- `uses-permission`: The manifest includes two permissions that the user must accept to allow the
  app to access location, `ACCESS_COARSE_LOCATION` and `ACCESS_FINE_LOCATION`.
- `application`: The application tag specifies some attributes about the entire app. For example,
  `android:name` means which class will be used to do the initial setup.
- `meta-data`: The only metadata I created is `com.google.android.geo.API_KEY`, which specifies the
  API key required for the Maps SDK and Places API.
- `activity`: The Activity is the initial entry point into the Android SDK. As recommended by
  Google, today's apps should have only one Activity (`MainActivity`).

**build.gradle.kts (project level)**: In this file, we specify the plugins required for some
libraries for the build to work. By default, we always suffix plugins with `apply false` because
they will only actually be applied at the `build.gradle.kts` module levels.

**build.gradle.kts (module level)**: In this file, we apply the plugins necessary for the module to
work. In addition, we specify some settings, such as the minimum Android version on which the
application will work and many others. One of the main responsibilities of this file is to declare
the application's dependencies.

**libs.versions.toml**: This file centralizes all plugin and library versions.

**secrets.properties (not sent due to sensitive content)**: Handles sensitive API keys that cannot
be shared within the VCS or with unnecessary people.

**AppRoutes.kt**: file to define all the routes for the application. There are four routes in the
application: **Home**, **Tip**, **NewPhoto** and **Visualizer**. Some of these routes need to
receive some arguments to work properly.

**MainActivity.kt**: The MainActivity, as specified earlier, is the entry point of the Android SDK.
Here, we relate all the routes to their screens.

**PhotoMapApplication.kt**: The PhotoMapApplication is the custom application class to setup the
application. Here we specify which Places API to use and enable Hilt as the application's DI (
dependency injection).

**PhotoMapDI.kt**: PhotoMapDI provides all class dependencies. In this project, we need to provide
the following classes: **PhotoDatabase**, **PhotosDao**, and **PlacesClient**.

### Home folder

The home folder stores all files related to the home screen.

**HomeViewModel.kt**: The ViewModel used by the home screen to handle the autocomplete feature.

**PermissionBox.kt**: A custom UI component designed to wrap other components and request specific
permissions when the components are displayed on screen.

**PhotoGoogleMap.kt**: A user interface wrapper to display the Google Map with specific styling and
settings.

**HomeScreen.kt**: The main screen of the application with all the components shown in the
screenshots, such as the map, the search bar, and the buttons with all the necessary data and
events.

**HomeScreenNavigation.kt**: a wrapper which creates a function to relate the `Home` route with the
`HomeScreen`.

### Photo folder

**PhotoDatabase**: It is an abstract class that represents the local database with some information
such as database version, entities and all created DAOs needed to execute queries.

**PhotosDao.kt**: PhotosDao is a DAO (Data Access Object) for executing database queries on the
`photos` table.

**PhotosEntity.kt**: PhotosEntity is the data class which represents the `photos` table with all
columns.

> 💡 **Important to know**
> The three files above use annotations from the Room library. Room is the ORM used in the Android
> SDK. Therefore, when we use these annotations, several codes are generated under the hood so that
> they actually work.

**PhotosRepository.kt**: This is a repository created to control which data sources will be used to
manipulate the photo model. Since the application only has one data source (local database), this is
the only place the repository will retrieve data from.

**NewPhotoViewModel.kt**: The ViewModel used by the new photo screen. It's responsible for create
the photo in the app internal storage and save the latitude, longitude and UUID in the data source.

**NewPhotoScreen.kt**: The screen responsible for showing which latitude and longitude the user
chooses to mark with a photo, choosing the photo itself and saving it.

**NewPhotoScreenNavigation.kt**: a wrapper which creates a function to relate the `NewPhoto` route
with the `NewPhotoScreen`.

### Tip folder

**TipDialog.kt**: a dialog box that appears every time the user enters in the app which learn how to
add markers on the map.

**TipDialogNavigation.kt**: a wrapper which creates a function to relate the `Tip` route with the
`TipDialog`.

### Visualizer folder

**VisualizerViewModel.kt**: The ViewModel used by the new visualizer screen. It's responsible for
delete the photo in the app internal storage and delete the latitude, longitude and UUID in the data
source.

**VisualizerScreen.kt**: The screen responsible for showing the photo when clicking on the marker.
On this screen, the user can also delete the photo.

**VisualizerScreenNavigation.kt**: a wrapper which creates a function to relate the `Visualizer`
route with the `VisualizerScreen`.

# 📱 Screenshots

<p align="center">
  <img src="https://i.ibb.co/d4syXcrx/Screenshot-20250305-121831.png" width="30%"/>
  <img src="https://i.ibb.co/hxpzHBPQ/Screenshot-20250305-121820.png" width="30%"/>
  <img src="https://i.ibb.co/xtTc9jtg/Screenshot-20250305-121836.png" width="30%"/>
</p>

# 📚 Bibliography

In this section, you'll find some links and resources which explain about what libraries are used in
the project.

| Biblioteca            | Link                                                                              |
|-----------------------|-----------------------------------------------------------------------------------|
| Jetpack Compose       | https://developer.android.com/compose                                             |   
| kotlinx-serialization | https://github.com/Kotlin/kotlinx.serialization                                   |
| Coil                  | https://github.com/coil-kt/coil                                                   |
| Coroutines            | https://github.com/Kotlin/kotlinx.coroutines                                      |
| Hilt                  | https://developer.android.com/training/dependency-injection/hilt-android?hl=pt-br |
| Maps SDK              | https://developers.google.com/maps/documentation/android-sdk/overview?hl=pt-br    |
| Places API            | https://developers.google.com/maps/documentation/places/web-service?hl=pt-br      |
| KSP                   | https://github.com/google/ksp                                                     |
| Room                  | https://developer.android.com/training/data-storage/room?hl=pt-br                 |