package com.tommunyiri.saftechweather.data.sources.local.database

import com.tommunyiri.saftechweather.core.di.scope.IoDispatcher
import com.tommunyiri.saftechweather.data.sources.local.database.dao.WeatherDao
import com.tommunyiri.saftechweather.data.sources.local.database.entity.DBCurrentWeather
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

}
