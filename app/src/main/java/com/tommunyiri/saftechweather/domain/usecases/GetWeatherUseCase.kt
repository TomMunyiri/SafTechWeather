package com.tommunyiri.saftechweather.domain.usecases

import com.tommunyiri.saftechweather.core.utils.Result
import com.tommunyiri.saftechweather.domain.model.CurrentWeather
import com.tommunyiri.saftechweather.domain.model.LocationModel
import com.tommunyiri.saftechweather.domain.repository.WeatherRepository

class GetWeatherUseCase(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(
        location: LocationModel,
        refresh: Boolean,
    ): Result<CurrentWeather?> {
        val query = "${location.latitude},${location.longitude}"
        val result = weatherRepository.getCurrentWeather(query, refresh)
        if (refresh) {
            when (result) {
                is Result.Success -> {
                    if (result.data != null) {
                        weatherRepository.deleteWeatherData()
                        weatherRepository.storeWeatherData(result.data)
                    }
                }

                is Result.Error -> {}
                is Result.Loading -> {}
            }
        }
        return result
    }
}
