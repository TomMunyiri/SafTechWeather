package com.tommunyiri.saftechweather.data.sources.remote

import com.tommunyiri.saftechweather.core.di.scope.IoDispatcher
import com.tommunyiri.saftechweather.core.utils.Result
import com.tommunyiri.saftechweather.data.sources.remote.retrofit.WeatherApiService
import com.tommunyiri.saftechweather.domain.model.NetworkForecastday
import com.tommunyiri.saftechweather.domain.model.NetworkWeatherResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRemoteDataSourceImpl
    @Inject
    constructor(
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
        private val apiService: WeatherApiService,
    ) : WeatherRemoteDataSource {
        override suspend fun getCurrentWeather(query: String): Result<NetworkWeatherResponse> =
            withContext(ioDispatcher) {
                return@withContext try {
                    val result = apiService.getCurrentWeather(query)
                    if (result.isSuccessful) {
                        val networkWeatherResponse = result.body()
                        Result.Success(networkWeatherResponse)
                    } else {
                        Result.Success(null)
                    }
                } catch (exception: Exception) {
                    Result.Error(exception)
                }
            }

        override suspend fun getWeatherForecast(
            query: String,
            date: String,
        ): Result<List<NetworkForecastday>> =
            withContext(ioDispatcher) {
                return@withContext try {
                    val result = apiService.getWeatherForecast(query, date)
                    if (result.isSuccessful) {
                        val networkWeatherForecast = result.body()?.forecast?.forecastday
                        Result.Success(networkWeatherForecast)
                    } else {
                        Result.Success(null)
                    }
                } catch (exception: Exception) {
                    Result.Error(exception)
                }
            }
    }
