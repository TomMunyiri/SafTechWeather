package com.tommunyiri.saftechweather.core.di.module

import com.tommunyiri.saftechweather.data.settings.DefaultSettingsRepository
import com.tommunyiri.saftechweather.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    fun bindSettingsRepository(settingsRepository: DefaultSettingsRepository): SettingsRepository
}
