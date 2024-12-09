package com.tommunyiri.saftechweather.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tommunyiri.saftechweather.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Tom Munyiri on 03/12/2024.
 * Company:
 * Email:
 */

@HiltViewModel
class SettingsScreenViewModel
@Inject
constructor(private val settingsRepository: SettingsRepository) : ViewModel() {
    private val _settingsScreenState = MutableStateFlow(SettingsScreenState())
    val settingsScreenState: StateFlow<SettingsScreenState> = _settingsScreenState.asStateFlow()

    private fun saveDefaultTempUnit(temperatureUnit: String) {
        viewModelScope.launch {
            settingsRepository.setDefaultTempUnit(temperatureUnit)
        }
    }

    private fun getDefaultTempUnit() {
        viewModelScope.launch {
            settingsRepository.getDefaultTempUnit().collect {
                setState { copy(prefTempUnit = it) }
            }
        }
    }

    fun processIntent(settingsScreenIntent: SettingsScreenIntent) {
        when (settingsScreenIntent) {
            is SettingsScreenIntent.GetDefaultTempUnit -> getDefaultTempUnit()
            is SettingsScreenIntent.SaveDefaultTempUnit -> saveDefaultTempUnit(settingsScreenIntent.tempUnit)
            is SettingsScreenIntent.SaveTheme -> saveTheme(settingsScreenIntent.theme)
            SettingsScreenIntent.GetTheme -> getTheme()
        }
    }

    private fun setState(stateReducer: SettingsScreenState.() -> SettingsScreenState) {
        viewModelScope.launch {
            _settingsScreenState.emit(stateReducer(settingsScreenState.value))
        }
    }

    private fun saveTheme(theme: String) {
        viewModelScope.launch {
            settingsRepository.setPreferredTheme(theme)
            setState { copy(prefTheme = theme) }
        }
    }

    private fun getTheme() {
        viewModelScope.launch {
            settingsRepository.getPreferredTheme().collect { theme ->
                setState { copy(prefTheme = theme) }
            }
        }
    }
}


data class SettingsScreenState(
    val prefTempUnit: String? = null,
    val prefTheme: String? = null
)
