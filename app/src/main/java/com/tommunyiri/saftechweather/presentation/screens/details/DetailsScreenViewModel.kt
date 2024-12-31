package com.tommunyiri.saftechweather.presentation.screens.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tommunyiri.saftechweather.core.utils.Result
import com.tommunyiri.saftechweather.domain.model.Hour
import com.tommunyiri.saftechweather.domain.model.LocationModel
import com.tommunyiri.saftechweather.domain.repository.SettingsRepository
import com.tommunyiri.saftechweather.domain.usecases.WeatherUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
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
class DetailsScreenViewModel
@Inject
constructor(
    private val settingsRepository: SettingsRepository,
    private val weatherUseCases: WeatherUseCases,
) : ViewModel() {
    private val _detailsScreenState = MutableStateFlow(DetailsScreenState())
    val detailsScreenState: StateFlow<DetailsScreenState> = _detailsScreenState.asStateFlow()

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

    fun processIntent(detailsScreenIntent: DetailsScreenIntent) {
        when (detailsScreenIntent) {
            is DetailsScreenIntent.LoadWeatherData ->
                getWeather(
                    detailsScreenState.value.defaultLocation,
                    detailsScreenIntent.selectedDate,
                )

            is DetailsScreenIntent.RefreshWeatherData -> refreshWeather(
                detailsScreenState.value.defaultLocation,
                detailsScreenIntent.selectedDate,
            )
        }
    }

    private fun setState(stateReducer: DetailsScreenState.() -> DetailsScreenState) {
        viewModelScope.launch {
            _detailsScreenState.emit(stateReducer(detailsScreenState.value))
        }
    }

    /**
     *This attempts to get the [Hour] from the local data source,
     * if the result is null, it gets from the remote source.
     * @see refreshWeather
     */
    private fun getWeather(locationModel: LocationModel, selectedDate: String) {
        setIsWeatherLoading()
        viewModelScope.launch {
            when (
                val result =
                    weatherUseCases.getWeatherForecast(locationModel, selectedDate, false)
            ) {
                is Result.Success -> {
                    if (!result.data.isNullOrEmpty()) {
                        val weather = result.data
                        setState {
                            copy(
                                isLoading = false,
                                hourlyWeatherList = weather.first().hour,
                                error = null,
                                modifiedAt = weather.first().modifiedAt.toString(),
                                isWeatherUpToDate = weatherUseCases.determineIfWeatherUpdated(
                                    weather.first().modifiedAt.toString()
                                )
                            )
                        }
                    } else {
                        refreshWeather(locationModel, selectedDate)
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
     */
    private fun refreshWeather(locationModel: LocationModel, selectedDate: String) {
        setIsWeatherLoading()
        viewModelScope.launch {
            when (
                val result =
                    weatherUseCases.getWeatherForecast(locationModel, selectedDate, true)
            ) {
                is Result.Success -> {
                    if (!result.data.isNullOrEmpty()) {
                        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                        val currentDateTime = dateFormat.format(Date(System.currentTimeMillis()))
                        setState {
                            copy(
                                isLoading = false,
                                hourlyWeatherList = result.data.first().hour,
                                error = null,
                                modifiedAt = currentDateTime,
                                isWeatherUpToDate = weatherUseCases.determineIfWeatherUpdated(
                                    currentDateTime
                                )
                            )
                        }
                    } else {
                        setState {
                            copy(
                                isLoading = false,
                                error = "No weather data available for selected date"
                            )
                        }
                    }
                }

                is Result.Error -> {
                    Log.d("TAG", "getWeather: ERROR ${result.exception}")
                    setState {
                        copy(
                            isRefreshing = false,
                            isLoading = false,
                            error = result.exception.toString(),
                        )
                    }
                }

                is Result.Loading -> setState { copy(isLoading = true) }
            }
        }
    }

    private fun setIsWeatherLoading() {
        _detailsScreenState.update { currentState ->
            currentState.copy(
                isLoading = true,
                isRefreshing = true,
            )
        }
    }
}

data class DetailsScreenState(
    val hourlyWeatherList: List<Hour>? = null,
    val isLoading: Boolean = false,
    val isLoadingForecast: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val locationName: String = "-",
    val currentSystemTime: String = "",
    val modifiedAt: String = "",
    val cityName: String = "",
    val prefTempUnit: String? = null,
    val defaultLocation: LocationModel = LocationModel(0.0, 0.0),
    val isWeatherUpToDate: Boolean = false,
)
