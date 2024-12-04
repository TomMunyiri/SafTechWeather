package com.tommunyiri.saftechweather.data.mappers

import com.tommunyiri.saftechweather.domain.model.Forecastday
import com.tommunyiri.saftechweather.domain.model.NetworkForecastday

class WeatherForecastMapperRemote :
    BaseMapper<List<NetworkForecastday>, List<Forecastday>> {
    override fun transformToDomain(type: List<NetworkForecastday>): List<Forecastday> {
        return type.map { networkWeatherForecast ->
            Forecastday(
                networkWeatherForecast.astro,
                networkWeatherForecast.date,
                networkWeatherForecast.date_epoch,
                networkWeatherForecast.day,
                networkWeatherForecast.hour
            )
        }
    }

    override fun transformToDto(type: List<Forecastday>): List<NetworkForecastday> {
        return type.map { weatherForecast ->
            NetworkForecastday(
                weatherForecast.astro,
                weatherForecast.date,
                weatherForecast.date_epoch,
                weatherForecast.day,
                weatherForecast.hour
            )
        }
    }
}
