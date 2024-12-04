package com.tommunyiri.saftechweather.core.di.module

import com.tommunyiri.saftechweather.domain.repository.WeatherRepository
import com.tommunyiri.saftechweather.domain.usecases.GetWeatherForecastUseCase
import com.tommunyiri.saftechweather.domain.usecases.GetWeatherUseCase
import com.tommunyiri.saftechweather.domain.usecases.WeatherUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UseCasesModule {
    @Provides
    @Singleton
    fun provideWeatherUseCases(weatherRepository: WeatherRepository): WeatherUseCases {
        return WeatherUseCases(
            getWeather = GetWeatherUseCase(weatherRepository),
            getWeatherForecast = GetWeatherForecastUseCase(weatherRepository)
        )
    }
}
