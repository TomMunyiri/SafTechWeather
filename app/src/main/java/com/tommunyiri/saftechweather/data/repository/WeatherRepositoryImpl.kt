package com.tommunyiri.saftechweather.data.repository

import com.tommunyiri.saftechweather.core.di.scope.IoDispatcher
import com.tommunyiri.saftechweather.data.sources.local.database.WeatherLocalDataSource
import com.tommunyiri.saftechweather.data.sources.remote.WeatherRemoteDataSource
import com.tommunyiri.saftechweather.domain.model.CurrentWeather
import com.tommunyiri.saftechweather.domain.model.NetworkWeatherResponse
import com.tommunyiri.saftechweather.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


/**
 * Created by Tom Munyiri on 03/12/2024.
 * Company:
 * Email:
 */
class WeatherRepositoryImpl @Inject
constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val localDataSource: WeatherLocalDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : WeatherRepository {
    override suspend fun getCurrentWeather(location: String): Result<NetworkWeatherResponse?> {
        TODO("Not yet implemented")
    }
}