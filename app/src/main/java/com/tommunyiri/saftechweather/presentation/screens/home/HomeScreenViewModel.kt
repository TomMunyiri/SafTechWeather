package com.tommunyiri.saftechweather.presentation.screens.home

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tommunyiri.saftechweather.core.utils.Result
import com.tommunyiri.saftechweather.domain.model.CurrentWeather
import com.tommunyiri.saftechweather.domain.model.LocationModel
import com.tommunyiri.saftechweather.domain.repository.SettingsRepository
import com.tommunyiri.saftechweather.domain.usecases.WeatherUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

/**
 * Created by Tom Munyiri on 03/12/2024.
 * Company:
 * Email:
 */

@HiltViewModel
class HomeScreenViewModel
@Inject
constructor(
    private val settingsRepository: SettingsRepository,
    private val weatherUseCases: WeatherUseCases,
) : ViewModel() {
    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState: StateFlow<HomeScreenState> = _homeScreenState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                settingsRepository.getLanguage(),
                settingsRepository.getDefaultTempUnit(),
                settingsRepository.getDefaultLocation(),
            ) { language, prefTempUnit, defaultLocation ->
                Triple(language, prefTempUnit, defaultLocation)
            }.collect { (language, prefTempUnit, defaultLocation) ->
                setState {
                    copy(defaultLocation = defaultLocation, prefTempUnit = prefTempUnit)
                }
            }
        }
    }

    fun processIntent(homeScreenIntent: HomeScreenIntent) {
        when (homeScreenIntent) {
            is HomeScreenIntent.LoadWeatherData ->
                getWeather(homeScreenState.value.defaultLocation)

            is HomeScreenIntent.GetCurrentTimeDate ->
                setState { copy(currentSystemTime = getCurrentSystemTime()) }

            HomeScreenIntent.RefreshWeatherData ->
                refreshWeather(homeScreenState.value.defaultLocation)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentSystemTime(): String {
        val currentTime = System.currentTimeMillis()
        val date = Date(currentTime)
        val dateFormat = SimpleDateFormat("EEEE MMM d, hh:mm aaa", Locale.getDefault())
        return dateFormat.format(date)
    }

    private fun setState(stateReducer: HomeScreenState.() -> HomeScreenState) {
        viewModelScope.launch {
            _homeScreenState.emit(stateReducer(homeScreenState.value))
        }
    }

    /**
     *This attempts to get the [CurrentWeather] from the local data source,
     * if the result is null, it gets from the remote source.
     * @see refreshWeather
     */
    private fun getWeather(locationModel: LocationModel) {
        setIsWeatherLoading()
        viewModelScope.launch {
            when (val result = weatherUseCases.getWeather(locationModel, false)) {
                is Result.Success -> {
                    if (result.data != null) {
                        val weather = result.data
                        setState {
                            copy(
                                isLoading = false,
                                weather = weather,
                                error = null,
                                isWeatherUpToDate = weatherUseCases.determineIfWeatherUpdated(
                                    weather.last_updated
                                )
                            )
                        }
                    } else {
                        refreshWeather(locationModel)
                    }
                }

                is Result.Error -> {
                    setState {
                        copy(
                            isRefreshing = false,
                            isLoading = false,
                            error = result.exception.toString(),
                        )
                    }
                }

                is Result.Loading -> {
                    setState { copy(isLoading = true, error = null) }
                }
            }
        }
    }

    /**
     * This is called when the user swipes down to refresh.
     * This enables the [CurrentWeather] for the current [locationModel] to be received.
     */
    private fun refreshWeather(locationModel: LocationModel) {
        setIsWeatherLoading()
        viewModelScope.launch {
            when (val result = weatherUseCases.getWeather(locationModel, true)) {
                is Result.Success -> {
                    if (result.data != null) {
                        setState {
                            copy(
                                isLoading = false,
                                weather = result.data,
                                error = null,
                                isWeatherUpToDate = weatherUseCases.determineIfWeatherUpdated(result.data.last_updated)
                            )
                        }
                    } else {
                        setState {
                            copy(isLoading = false, error = "No weather data")
                        }
                    }
                }

                is Result.Error ->
                    setState {
                        copy(
                            isRefreshing = false,
                            isLoading = false,
                            error = result.exception.toString(),
                        )
                    }

                is Result.Loading ->
                    setState { copy(isLoading = true) }
            }
        }
    }

    private fun setIsWeatherLoading() {
        setState {
            copy(
                isLoading = true,
                isRefreshing = true,
            )
        }

    }
}

data class HomeScreenState(
    val weather: CurrentWeather? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val locationName: String = "-",
    val currentSystemTime: String = "",
    val cityName: String = "",
    val prefTempUnit: String? = null,
    val defaultLocation: LocationModel = LocationModel(0.0, 0.0),
    val isWeatherUpToDate: Boolean = false,
)
