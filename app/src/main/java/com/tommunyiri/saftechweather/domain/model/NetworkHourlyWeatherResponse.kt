package com.tommunyiri.saftechweather.domain.model

data class NetworkHourlyWeatherResponse(
    val forecast: Forecast,
    val location: NetworkLocation
)