package com.tommunyiri.saftechweather.presentation.screens.details

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tommunyiri.saftechweather.core.utils.Result
import com.tommunyiri.saftechweather.data.sources.remote.retrofit.WeatherApiService
import com.tommunyiri.saftechweather.domain.model.Forecastday
import com.tommunyiri.saftechweather.domain.model.Hour
import com.tommunyiri.saftechweather.domain.model.LocationModel
import com.tommunyiri.saftechweather.domain.model.NetworkForecastday
import com.tommunyiri.saftechweather.domain.repository.SettingsRepository
import com.tommunyiri.saftechweather.domain.usecases.WeatherUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Tom Munyiri on 03/12/2024.
 * Company:
 * Email:
 */

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val context: Application,
    private val weatherUseCases: WeatherUseCases,
    private val apiService: WeatherApiService
) : ViewModel() {
    private val _detailsScreenState = MutableStateFlow(DetailsScreenState())
    val detailsScreenState: StateFlow<DetailsScreenState> = _detailsScreenState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                settingsRepository.getLanguage(),
                settingsRepository.getDefaultTempUnit(),
                settingsRepository.getDefaultLocation()
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
            is DetailsScreenIntent.LoadWeatherData -> getWeather(
                detailsScreenState.value.defaultLocation,
                //LocationModel(3477.434, 12.324),
                detailsScreenIntent.selectedDate
            )
        }
    }


    private fun setState(stateReducer: DetailsScreenState.() -> DetailsScreenState) {
        viewModelScope.launch {
            _detailsScreenState.emit(stateReducer(detailsScreenState.value))
        }
    }

    /**
     *This attempts to get the [Weather] from the local data source,
     * if the result is null, it gets from the remote source.
     * @see refreshWeather
     */
    private fun getWeather(locationModel: LocationModel, selectedDate: String) {
        setIsWeatherLoading()
        viewModelScope.launch {
            when (val result =
                weatherUseCases.getWeatherForecast(locationModel, selectedDate, false)) {
                is Result.Success -> {

                    refreshWeather(locationModel, selectedDate)

                    /*if (!result.data.isNullOrEmpty()) {
                        val weather = result.data
                        Log.d("TAG", "getWeather: INITIAL ${result}. Size: ${weather.size}")
                        setState {
                            copy(
                                isLoading = false, hourlyWeatherList = weather, error = null
                            )
                        }
                    } else {
                        refreshWeather(locationModel, selectedDate)
                    }*/
                }

                is Result.Error -> {
                    Log.d("TAG", "getWeather: ERROR")
                    setState {
                        copy(
                            isRefreshing = false,
                            isLoading = false,
                            error = result.exception.toString()
                        )
                    }
                }

                is Result.Loading -> {
                    Log.d("TAG", "getWeather: LOADING")
                    setState { copy(isLoading = true, error = null) }
                }
            }
        }
    }

    /**
     * This is called when the user swipes down to refresh.
     * This enables the [Weather] for the current [location] to be received.
     */
    private fun refreshWeather(locationModel: LocationModel, selectedDate: String) {
        setIsWeatherLoading()
        viewModelScope.launch {

            try {
                val query = "${locationModel.latitude},${locationModel.longitude}"
                val result = apiService.getWeatherForecast(query, selectedDate)
                if (result.isSuccessful) {
                    val networkWeatherForecast = result.body()?.forecast?.forecastday
                    Result.Success(networkWeatherForecast)
                    setState {
                        copy(
                            isLoading = false,
                            hourlyWeatherList = networkWeatherForecast?.first()?.hour,
                            error = null,
                        )
                    }
                } else {
                    Result.Success(null)
                    setState {
                        copy(isLoading = false, error = "No weather data")
                    }
                }
            } catch (exception: Exception) {
                Result.Error(exception)
                setState {
                    copy(
                        isRefreshing = false,
                        isLoading = false,
                        error = exception.toString(),
                    )
                }
            }

            /*when (val result =
                weatherUseCases.getWeatherForecast(locationModel, selectedDate, false)) {
                is Result.Success -> {
                    if (result.data != null) {
                        Log.d(
                            "TAG", "getWeather: REFRESH SUCCESS $result. Size: ${result.data.size}"
                        )
                        setState {
                            copy(
                                isLoading = false,
                                hourlyWeatherList = result.data,
                                error = null,
                            )
                        }
                    } else {
                        Log.d("TAG", "getWeather: SUCCESS RESULT NULL $result")
                        setState {
                            copy(isLoading = false, error = "No weather data")
                        }
                    }
                }

                is Result.Error -> {
                    Log.d("TAG", "getWeather: ERROR ${result.exception.toString()}")
                    setState {
                        copy(
                            isRefreshing = false,
                            isLoading = false,
                            error = result.exception.toString(),
                        )
                    }
                }

                is Result.Loading -> setState { copy(isLoading = true) }
            }*/
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
//    val weatherForecastList: List<WeatherForecast>? = null,
    //val hourlyWeatherList: List<Forecastday>? = null,
    val hourlyWeatherList: List<Hour>? = null,
    val isLoading: Boolean = false,
    val isLoadingForecast: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val locationName: String = "-",
    val currentSystemTime: String = "",
    val cityName: String = "",
    val prefTempUnit: String? = null,
    val defaultLocation: LocationModel = LocationModel(0.0, 0.0),
)