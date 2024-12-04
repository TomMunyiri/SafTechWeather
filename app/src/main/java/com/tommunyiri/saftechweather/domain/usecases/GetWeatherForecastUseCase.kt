package com.tommunyiri.saftechweather.domain.usecases

import com.tommunyiri.saftechweather.core.utils.Result
import com.tommunyiri.saftechweather.domain.model.Forecastday
import com.tommunyiri.saftechweather.domain.repository.WeatherRepository

class GetWeatherForecastUseCase(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(
        query: String, date: String, url: String, refresh: Boolean
    ): Result<List<Forecastday>?> {
        val result = weatherRepository.getForecastData(query, date, url, refresh)
        if (refresh) {
            when (result) {
                is Result.Success -> {
                    if (result.data != null) {
                        weatherRepository.deleteForecastData()
                        weatherRepository.storeForecastData(result.data)
                    }
                }

                is Result.Error -> {}
                is Result.Loading -> {}
            }
        }
        return result
    }
}
