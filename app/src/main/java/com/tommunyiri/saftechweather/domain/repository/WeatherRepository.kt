package com.tommunyiri.saftechweather.domain.repository

import com.tommunyiri.saftechweather.domain.model.NetworkWeatherResponse


/**
 * Created by Tom Munyiri on 03/12/2024.
 * Company:
 * Email:
 */
interface WeatherRepository {
    suspend fun getCurrentWeather(location: String): Result<NetworkWeatherResponse?>
}