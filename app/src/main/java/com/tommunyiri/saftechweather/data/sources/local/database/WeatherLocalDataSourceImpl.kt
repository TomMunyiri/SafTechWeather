package com.tommunyiri.saftechweather.data.sources.local.database

import com.tommunyiri.saftechweather.core.di.scope.IoDispatcher
import com.tommunyiri.saftechweather.data.sources.local.database.dao.WeatherDao
import com.tommunyiri.saftechweather.data.sources.local.database.entity.DBCurrentWeather
import com.tommunyiri.saftechweather.data.sources.local.database.entity.DBForecastday
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherLocalDataSourceImpl
    @Inject
    constructor(
        private val weatherDao: WeatherDao,
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    ) : WeatherLocalDataSource {
        override suspend fun getWeather(): DBCurrentWeather =
            withContext(ioDispatcher) {
                return@withContext weatherDao.getWeather()
            }

        override suspend fun saveWeather(weather: DBCurrentWeather) =
            withContext(ioDispatcher) {
                weatherDao.insertWeather(weather)
            }

        override suspend fun deleteWeather() =
            withContext(ioDispatcher) {
                weatherDao.deleteAllWeather()
            }

        override suspend fun getForecastWeather(date: String): List<DBForecastday>? =
            withContext(ioDispatcher) {
                return@withContext weatherDao.getAllWeatherForecast(date)
            }

        override suspend fun saveForecastWeather(weatherForecast: DBForecastday) =
            withContext(ioDispatcher) {
                weatherDao.insertForecastWeather(weatherForecast)
            }

        override suspend fun deleteForecastWeather() =
            withContext(ioDispatcher) {
                weatherDao.deleteAllWeatherForecast()
            }
    }
