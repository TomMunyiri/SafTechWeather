package com.tommunyiri.saftechweather.data.repository

import com.tommunyiri.saftechweather.core.di.scope.IoDispatcher
import com.tommunyiri.saftechweather.data.sources.local.database.WeatherLocalDataSource
import com.tommunyiri.saftechweather.data.sources.remote.WeatherRemoteDataSource
import com.tommunyiri.saftechweather.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.tommunyiri.saftechweather.core.utils.Result
import com.tommunyiri.saftechweather.data.mappers.WeatherMapperLocal
import com.tommunyiri.saftechweather.data.mappers.WeatherMapperRemote
import com.tommunyiri.saftechweather.domain.model.CurrentWeather
import com.tommunyiri.saftechweather.domain.model.NetworkCurrentWeather

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
    override suspend fun getCurrentWeather(
        location: String,
        refresh: Boolean
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

    override suspend fun storeWeatherData(weather: CurrentWeather) = withContext(ioDispatcher) {
        val mapper = WeatherMapperLocal()
        localDataSource.saveWeather(mapper.transformToDto(weather))
    }

    override suspend fun deleteWeatherData() =
        withContext(ioDispatcher) {
            localDataSource.deleteWeather()
        }
}