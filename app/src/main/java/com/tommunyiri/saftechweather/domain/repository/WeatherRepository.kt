package com.tommunyiri.saftechweather.domain.repository

import com.tommunyiri.saftechweather.core.utils.Result
import com.tommunyiri.saftechweather.domain.model.CurrentWeather


/**
 * Created by Tom Munyiri on 03/12/2024.
 * Company:
 * Email:
 */
interface WeatherRepository {
    suspend fun getCurrentWeather(
        location: String,
        refresh: Boolean
    ): Result<CurrentWeather>

    suspend fun storeWeatherData(weather: CurrentWeather)

    suspend fun deleteWeatherData()
}