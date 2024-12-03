package com.tommunyiri.saftechweather.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tommunyiri.saftechweather.domain.model.LocationModel
import com.tommunyiri.saftechweather.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Tom Munyiri on 03/12/2024.
 * Company:
 * Email:
 */

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    //private val weatherRepository: WeatherRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState: StateFlow<HomeScreenState> = _homeScreenState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                settingsRepository.getLanguage(),
                settingsRepository.getUnits(),
                settingsRepository.getDefaultLocation()
            ) { language, units, defaultLocation ->
                Triple(language, units, defaultLocation)
            }.collect { (language, units, defaultLocation) ->
                setState {
                    copy(
                        defaultLocation = defaultLocation
                    )
                }
            }
        }
    }

    fun processIntent(homeScreenIntent: HomeScreenIntent) {
        when (homeScreenIntent) {
            /*is HomeScreenIntent.LoadWeatherData -> {
                viewModelScope.launch {
                    val result = weatherRepository.fetchWeatherData(
                        language = state.value.language,
                        defaultLocation = state.value.defaultLocation,
                        units = state.value.units
                    )
                    processResult(result)
                }
            }*/

            is HomeScreenIntent.DisplayCityName -> {
                setState { copy(locationName = homeScreenIntent.cityName) }
            }
        }
    }

    private fun setState(stateReducer: HomeScreenState.() -> HomeScreenState) {
        viewModelScope.launch {
            _homeScreenState.emit(stateReducer(homeScreenState.value))
        }
    }
}

data class HomeScreenState(
//    val weatherForecastList: List<WeatherForecast>? = null,
//    val weather: Weather? = null,
    val isLoading: Boolean = false,
    val isLoadingForecast: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val locationName: String = "-",
    val defaultLocation: LocationModel = LocationModel(0.0, 0.0),
)