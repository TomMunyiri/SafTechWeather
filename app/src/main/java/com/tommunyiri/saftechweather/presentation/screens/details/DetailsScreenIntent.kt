package com.tommunyiri.saftechweather.presentation.screens.details

/**
 * Created by Tom Munyiri on 03/12/2024.
 * Company:
 * Email:
 */
sealed class DetailsScreenIntent {
    data class LoadWeatherData(val selectedDate: String) : DetailsScreenIntent()
    data class RefreshWeatherData(val selectedDate: String) : DetailsScreenIntent()
}
