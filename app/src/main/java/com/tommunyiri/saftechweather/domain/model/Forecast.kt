package com.tommunyiri.saftechweather.domain.model

data class Forecast(
    val forecastday: List<NetworkForecastday>
)