package com.tommunyiri.saftechweather.core

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tommunyiri.saftechweather.domain.model.LocationModel
import com.tommunyiri.saftechweather.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(MainViewState())
    val state: StateFlow<MainViewState> = _state.asStateFlow()

    fun processIntent(mainViewIntent: MainViewIntent) {
        when (mainViewIntent) {
            is MainViewIntent.GrantPermission -> {
                setState { copy(isPermissionGranted = mainViewIntent.isGranted) }
            }

            is MainViewIntent.CheckLocationSettings -> {
                setState { copy(isLocationSettingEnabled = mainViewIntent.isEnabled) }
            }

            is MainViewIntent.ReceiveLocation -> {
                getDefaultLocation(mainViewIntent.longitude, mainViewIntent.latitude)
            }

            is MainViewIntent.GetTheme -> getTheme()
        }
    }

    private fun getDefaultLocation(longitude: Double, latitude: Double) {
        val defaultLocation =
            LocationModel(longitude = longitude, latitude = latitude)
        viewModelScope.launch {
            settingsRepository.setDefaultLocation(defaultLocation)
        }
        setState { copy(defaultLocation = defaultLocation) }
    }

    private fun getTheme() {
        viewModelScope.launch {
            settingsRepository.getPreferredTheme().collect { theme ->
                setState { copy(theme = theme) }
            }
        }
    }

    private fun setState(stateReducer: MainViewState.() -> MainViewState) {
        viewModelScope.launch {
            _state.emit(stateReducer(state.value))
        }
    }
}

data class MainViewState(
    val isPermissionGranted: Boolean = false,
    val isLocationSettingEnabled: Boolean = false,
    val defaultLocation: LocationModel? = null,
    val theme: String? = null,
)

sealed class MainViewIntent {
    data class GrantPermission(val isGranted: Boolean) : MainViewIntent()

    data class CheckLocationSettings(val isEnabled: Boolean) : MainViewIntent()

    data class ReceiveLocation(val latitude: Double, val longitude: Double) : MainViewIntent()

    data object GetTheme : MainViewIntent()
}
