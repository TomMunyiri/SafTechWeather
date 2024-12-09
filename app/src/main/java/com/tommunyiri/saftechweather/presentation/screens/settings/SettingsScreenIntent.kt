package com.tommunyiri.saftechweather.presentation.screens.settings


/**
 * Created by Tom Munyiri on 06/12/2024.
 * Company:
 * Email:
 */
sealed class SettingsScreenIntent {
    object GetDefaultTempUnit : SettingsScreenIntent()
    data class SaveDefaultTempUnit(val tempUnit: String) : SettingsScreenIntent()
    data class SaveTheme(val theme: String) : SettingsScreenIntent()
    object GetTheme : SettingsScreenIntent()
}