package com.tommunyiri.saftechweather.data.mappers

import com.tommunyiri.saftechweather.data.sources.local.database.entity.DBForecastday
import com.tommunyiri.saftechweather.domain.model.Forecastday
import kotlin.collections.map

class WeatherForecastMapperLocal : BaseMapper<List<DBForecastday>, List<Forecastday>> {
    override fun transformToDomain(type: List<DBForecastday>): List<Forecastday> {
        return type.map { dbWeatherForecast ->
            Forecastday(
                dbWeatherForecast.astro,
                dbWeatherForecast.date,
                dbWeatherForecast.date_epoch,
                dbWeatherForecast.day,
                dbWeatherForecast.hour
            )
        }
    }

    override fun transformToDto(type: List<Forecastday>): List<DBForecastday> {
        return type.map { weatherForecast ->
            DBForecastday(
                weatherForecast.astro,
                weatherForecast.date,
                weatherForecast.date_epoch,
                weatherForecast.day,
                weatherForecast.hour
            )
        }
    }
}
