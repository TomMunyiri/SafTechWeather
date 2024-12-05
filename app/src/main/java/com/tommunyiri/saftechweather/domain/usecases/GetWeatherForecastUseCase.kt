package com.tommunyiri.saftechweather.domain.usecases

import android.util.Log
import com.tommunyiri.saftechweather.core.utils.Result
import com.tommunyiri.saftechweather.domain.model.Forecastday
import com.tommunyiri.saftechweather.domain.model.LocationModel
import com.tommunyiri.saftechweather.domain.repository.WeatherRepository

class GetWeatherForecastUseCase(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(
        location: LocationModel,
        date: String,
        refresh: Boolean,
    ): Result<List<Forecastday>?> {
        val query = "${location.latitude},${location.longitude}"
        val result = weatherRepository.getForecastData(query, date, refresh)
        if (refresh) {
            when (result) {
                is Result.Success -> {
                    if (result.data != null) {
                        weatherRepository.deleteForecastData()
                        weatherRepository.storeForecastData(result.data)
                    }
                }

                is Result.Error -> {
                    Log.d("TAG", "getWeatherForecast: ERROR ${result.exception}")
                }

                is Result.Loading -> {}
            }
        }
        return result
    }
}
