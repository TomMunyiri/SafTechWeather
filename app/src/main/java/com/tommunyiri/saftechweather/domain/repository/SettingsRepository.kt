package com.tommunyiri.saftechweather.domain.repository

import com.tommunyiri.saftechweather.domain.model.LocationModel
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun setLanguage(language: String)

    suspend fun getLanguage(): Flow<String>

    suspend fun setUnits(units: String)

    suspend fun getUnits(): Flow<String>

    fun getAppVersion(): String

    fun getAvailableLanguages(): List<String>

    fun getAvailableUnits(): List<String>

    suspend fun setDefaultLocation(defaultLocation: LocationModel)

    suspend fun getDefaultLocation(): Flow<LocationModel>

    suspend fun getFormat(): Flow<String>

    suspend fun setFormat(format: String)

    fun getFormats(): List<String>

    suspend fun setDefaultTempUnit(prefTempUnit: String)

    suspend fun getDefaultTempUnit(): Flow<String>

    suspend fun setPreferredTheme(preferredTheme: String)

    suspend fun getPreferredTheme(): Flow<String>
}
