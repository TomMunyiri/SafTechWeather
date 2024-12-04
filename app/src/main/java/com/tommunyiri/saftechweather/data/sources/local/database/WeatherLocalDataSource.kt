package com.tommunyiri.saftechweather.data.sources.local.database

import com.tommunyiri.saftechweather.data.sources.local.database.entity.DBCurrentWeather
import com.tommunyiri.saftechweather.data.sources.local.database.entity.DBForecastday

interface WeatherLocalDataSource {
    suspend fun getWeather(): DBCurrentWeather?

    suspend fun saveWeather(weather: DBCurrentWeather)

    suspend fun deleteWeather()

    suspend fun getForecastWeather(): List<DBForecastday>?

    suspend fun saveForecastWeather(weatherForecast: DBForecastday)

    suspend fun deleteForecastWeather()
}
