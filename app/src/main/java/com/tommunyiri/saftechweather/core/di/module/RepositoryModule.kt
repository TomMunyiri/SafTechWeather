package com.tommunyiri.saftechweather.core.di.module

import com.tommunyiri.saftechweather.data.repository.WeatherRepositoryImpl
import com.tommunyiri.saftechweather.data.settings.DefaultSettingsRepository
import com.tommunyiri.saftechweather.domain.repository.SettingsRepository
import com.tommunyiri.saftechweather.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindSettingsRepository(settingsRepository: DefaultSettingsRepository): SettingsRepository

    @Binds
    abstract fun bindRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository
}
