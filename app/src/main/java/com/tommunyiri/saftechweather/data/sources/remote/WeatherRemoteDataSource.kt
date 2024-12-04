package com.tommunyiri.saftechweather.data.sources.remote

import com.tommunyiri.saftechweather.core.utils.Result
import com.tommunyiri.saftechweather.domain.model.NetworkForecastday
import com.tommunyiri.saftechweather.domain.model.NetworkWeatherResponse

interface WeatherRemoteDataSource {
    suspend fun getCurrentWeather(query: String): Result<NetworkWeatherResponse>

    suspend fun getWeatherForecast(
        query: String,
        date: String,
    ): Result<List<NetworkForecastday>>
}
