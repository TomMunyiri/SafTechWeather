# SafTechWeather :partly_sunny:

An Android weather application implemented using the MVI pattern and clean architecture, Retrofit2, Dagger Hilt, Kotlin Flows, ViewModel, Coroutines, Room, Jetpack Compose, Kotlin DSL and some other libraries from the [Android Jetpack](https://developer.android.com/jetpack) . SafTech Weather fetches data from the [Weather API](https://www.weatherapi.com/api-explorer.aspx) to provide real time weather information. 

## Architecture
The architecture of this application relies and complies with the following points below:
* Pattern [Model-View-Intent](https://krishanmadushankadev.medium.com/android-mvi-model-view-intent-architecture-example-code-bc7dc8edb33)(MVI) with Clean Architecture which facilitates separation of different layers of the application.
* [Android architecture components](https://developer.android.com/topic/libraries/architecture/) which help to keep the application robust, testable, and maintainable.

<p align="center"><a><img src="https://github.com/TomMunyiri/DVTWeatherApp/blob/main/media/clean_architecture.png" width="700" alt="clean_architecture_image"></a></p>

## Technologies used:

* [Jetpack Compose](https://developer.android.com/jetpack/compose) - A modern toolkit for building native Android UI
* [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) - An alternative syntax for writing Gradle build scripts using Kotlin.
* [Version Catalogs](https://developer.android.com/build/migrate-to-catalogs) - A scalable way of maintaining dependencies and plugins in a multi-module project.
* [Retrofit](https://square.github.io/retrofit/) a REST Client for Android which makes it relatively easy to retrieve and upload JSON (or other structured data) via a REST based webservice.
* [Dagger Hilt](https://dagger.dev/hilt/) for dependency injection.
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) to store and manage UI-related data in a lifecycle conscious way.
* [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow#:~:text=StateFlow%20is%20a%20state%2Dholder,property%20of%20the%20MutableStateFlow%20class) to enable flows to emit updated state and emit values to multiple consumers optimally.
* [Kotlin Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html) to emit a stream of data with multiple values sequentially.
* [Timber](https://github.com/JakeWharton/timber) - a logger with a small, extensible API which provides utility on top of Android's normal Log class.
* [Material Design](https://material.io/develop/android/docs/getting-started/) an adaptable system of guidelines, components, and tools that support the best practices of user interface design.
* [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) used to manage the local storage i.e. `writing to and reading from the database`. Coroutines help in managing background threads and reduces the need for callbacks.
* [Room](https://developer.android.com/topic/libraries/architecture/room) persistence library which provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
* [Paging Library](https://developer.android.com/topic/libraries/architecture/paging) helps you load and display small chunks of data at a time.
* [Android KTX](https://developer.android.com/kotlin/ktx) which helps to write more concise, idiomatic Kotlin code.

## Features
- [ ] :TO BE UPDATED

## Installation
SafTech Weather requires a minimum API level of 24. Clone the repository. You will need an API key i.e. `API_KEY` from [Weather API](https://www.weatherapi.com/api-explorer.aspx) to request data. If you don’t already have an account, you will need to create one in order to request an API Key.

In your project's root directory, inside the `local.properties` file (create one if unavailable) include the following lines:

````properties
BASE_URL = "http://api.weatherapi.com/v1/"
API_KEY = "YOUR_API_KEY"

````

[//]: # (## Contribution)

[//]: # ()
[//]: # (![Alt]&#40;https://repobeats.axiom.co/api/embed/84dfd3cd94832805dbcaa3569ec855d19e5c9401.svg "Repobeats analytics image"&#41;)

## LICENSE
```
MIT License

Copyright (c) 2024 Tom Munyiri

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

