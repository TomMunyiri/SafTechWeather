package com.tommunyiri.saftechweather.data.repository

import android.util.Log
import com.tommunyiri.saftechweather.core.di.scope.IoDispatcher
import com.tommunyiri.saftechweather.core.utils.Result
import com.tommunyiri.saftechweather.data.mappers.WeatherForecastMapperLocal
import com.tommunyiri.saftechweather.data.mappers.WeatherForecastMapperRemote
import com.tommunyiri.saftechweather.data.mappers.WeatherMapperLocal
import com.tommunyiri.saftechweather.data.mappers.WeatherMapperRemote
import com.tommunyiri.saftechweather.data.sources.local.database.WeatherLocalDataSource
import com.tommunyiri.saftechweather.data.sources.remote.WeatherRemoteDataSource
import com.tommunyiri.saftechweather.domain.model.CurrentWeather
import com.tommunyiri.saftechweather.domain.model.Forecastday
import com.tommunyiri.saftechweather.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Tom Munyiri on 03/12/2024.
 * Company:
 * Email:
 */
class WeatherRepositoryImpl
    @Inject
    constructor(
        private val remoteDataSource: WeatherRemoteDataSource,
        private val localDataSource: WeatherLocalDataSource,
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    ) : WeatherRepository {
        override suspend fun getCurrentWeather(
            location: String,
            refresh: Boolean,
        ): Result<CurrentWeather> =
            withContext(ioDispatcher) {
                if (refresh) {
                    val mapper = WeatherMapperRemote()
                    when (val response = remoteDataSource.getCurrentWeather(location)) {
                        is Result.Success -> {
                            if (response.data != null) {
                                Result.Success(mapper.transformToDomain(response.data.current))
                            } else {
                                Result.Success(null)
                            }
                        }

                        is Result.Error -> {
                            Result.Error(response.exception)
                        }

                        else -> Result.Loading
                    }
                } else {
                    val mapper = WeatherMapperLocal()
                    val weather = localDataSource.getWeather()
                    if (weather != null) {
                        Result.Success(mapper.transformToDomain(weather))
                    } else {
                        Result.Success(null)
                    }
                }
            }

        override suspend fun storeWeatherData(weather: CurrentWeather) =
            withContext(ioDispatcher) {
                val mapper = WeatherMapperLocal()
                localDataSource.saveWeather(mapper.transformToDto(weather))
            }

        override suspend fun deleteWeatherData() =
            withContext(ioDispatcher) {
                localDataSource.deleteWeather()
            }

        override suspend fun storeForecastData(forecasts: List<Forecastday>) =
            withContext(ioDispatcher) {
                val mapper = WeatherForecastMapperLocal()
                mapper.transformToDto(forecasts).let { listOfDbForecast ->
                    listOfDbForecast.forEach {
                        localDataSource.saveForecastWeather(it)
                    }
                }
            }

        override suspend fun getForecastData(
            query: String,
            date: String,
            refresh: Boolean,
        ): Result<List<Forecastday>?> =
            withContext(ioDispatcher) {
                if (refresh) {
                    val mapper = WeatherForecastMapperRemote()
                    when (val response = remoteDataSource.getWeatherForecast(query, date)) {
                        is Result.Success -> {
                            Log.d("TAG", "getWeatherForecast: Success: $response")
                            if (response.data != null) {
                                Result.Success(mapper.transformToDomain(response.data))
                            } else {
                                Result.Success(null)
                            }
                        }

                        is Result.Error -> {
                            Log.d("TAG", "getWeatherForecast: Exception: ${response.exception}")
                            Result.Error(response.exception)
                        }

                        else -> Result.Loading
                    }
                } else {
                    val mapper = WeatherForecastMapperLocal()
                    val forecast = localDataSource.getForecastWeather(date)
                    if (forecast != null) {
                        Result.Success(mapper.transformToDomain(forecast))
                    } else {
                        Result.Success(null)
                    }
                }
            }

        override suspend fun deleteForecastData() =
            withContext(ioDispatcher) {
                localDataSource.deleteWeather()
            }
    }
