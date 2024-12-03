package com.tommunyiri.saftechweather.core

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
class MainViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
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
                val defaultLocation = LocationModel(
                    longitude = mainViewIntent.longitude,
                    latitude = mainViewIntent.latitude
                )
                viewModelScope.launch {
                    settingsRepository.setDefaultLocation(defaultLocation)
                }
                setState { copy(defaultLocation = defaultLocation) }
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
    val defaultLocation: LocationModel? = null
)

sealed class MainViewIntent {

    data class GrantPermission(val isGranted: Boolean) : MainViewIntent()

    data class CheckLocationSettings(val isEnabled: Boolean) : MainViewIntent()

    data class ReceiveLocation(val latitude: Double, val longitude: Double) : MainViewIntent()

}
