package com.tommunyiri.saftechweather.presentation.screens.settings

import androidx.lifecycle.ViewModel
import com.tommunyiri.saftechweather.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Tom Munyiri on 03/12/2024.
 * Company:
 * Email:
 */

@HiltViewModel
class SettingsScreenViewModel
    @Inject
    constructor(
        // private val weatherRepository: WeatherRepository,
        private val settingsRepository: SettingsRepository,
    ) : ViewModel()
