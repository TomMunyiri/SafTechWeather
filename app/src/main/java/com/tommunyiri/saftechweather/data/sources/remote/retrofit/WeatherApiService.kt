package com.tommunyiri.saftechweather.data.sources.remote.retrofit

import com.tommunyiri.saftechweather.domain.model.NetworkWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    /**
     * This function gets the [com.tommunyiri.saftechweather.domain.model.NetworkWeatherResponse] for the [location] the
     * user searched for.
     */
    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("q") location: String,
    ): Response<NetworkWeatherResponse>
}
