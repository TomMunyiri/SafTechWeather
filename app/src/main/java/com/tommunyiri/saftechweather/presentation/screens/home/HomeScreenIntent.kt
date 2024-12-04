package com.tommunyiri.saftechweather.presentation.screens.home

/**
 * Created by Tom Munyiri on 03/12/2024.
 * Company:
 * Email:
 */
sealed class HomeScreenIntent {
    data object GetCurrentTimeDate : HomeScreenIntent()

    object LoadWeatherData : HomeScreenIntent()

    object RefreshWeatherData : HomeScreenIntent()
}
