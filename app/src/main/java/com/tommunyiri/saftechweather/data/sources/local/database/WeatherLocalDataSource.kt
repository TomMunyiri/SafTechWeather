package com.tommunyiri.saftechweather.data.sources.local.database

import com.tommunyiri.saftechweather.data.sources.local.database.entity.DBCurrentWeather

interface WeatherLocalDataSource {
    suspend fun getWeather(): DBCurrentWeather?

    suspend fun saveWeather(weather: DBCurrentWeather)

    suspend fun deleteWeather()
}
