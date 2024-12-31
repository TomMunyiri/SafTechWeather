package com.tommunyiri.saftechweather.domain.usecases

data class WeatherUseCases(
    val getWeather: GetWeatherUseCase,
    val getWeatherForecast: GetWeatherForecastUseCase,
    val determineIfWeatherUpdated: DetermineIfWeatherUpdatedUseCase,
)
