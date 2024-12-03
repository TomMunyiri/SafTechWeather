package com.tommunyiri.saftechweather.domain.model

data class NetworkWeatherResponse(
    val current: NetworkCurrentWeather,
    val location: NetworkLocation
)