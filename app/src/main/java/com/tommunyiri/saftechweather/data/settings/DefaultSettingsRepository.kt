package com.tommunyiri.saftechweather.data.settings

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.tommunyiri.saftechweather.BuildConfig
import com.tommunyiri.saftechweather.data.dataStore
import com.tommunyiri.saftechweather.domain.model.LocationModel
import com.tommunyiri.saftechweather.domain.repository.SettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultSettingsRepository
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : SettingsRepository {
        private val PREF_LANGUAGE by lazy { stringPreferencesKey(KEY_LANGUAGE) }
        private val PREF_UNITS by lazy { stringPreferencesKey(KEY_UNITS) }
        private val PREF_TEMP_UNIT by lazy { stringPreferencesKey(KEY_TEMP_UNIT) }
        private val TIME_FORMAT by lazy { stringPreferencesKey(KEY_TIME_FORMAT) }
        private val PREF_LAT_LNG by lazy { stringPreferencesKey(KEY_LAT_LNG) }

        override suspend fun setLanguage(language: String) {
            set(key = PREF_LANGUAGE, value = language)
        }

        override suspend fun getLanguage(): Flow<String> {
            return flowOf("")
        }

        override suspend fun setUnits(units: String) {
            set(key = PREF_UNITS, value = units)
        }

        override suspend fun getUnits(): Flow<String> {
            return flowOf("")
        }

        override fun getAppVersion(): String = "Version : ${BuildConfig.VERSION_NAME}-${BuildConfig.BUILD_TYPE}"

        override fun getAvailableLanguages(): List<String> {
            TODO("Not yet implemented")
        }

        override fun getAvailableUnits(): List<String> {
            TODO("Not yet implemented")
        }

        override suspend fun setDefaultLocation(defaultLocation: LocationModel) {
            set(key = PREF_LAT_LNG, value = "${defaultLocation.latitude}/${defaultLocation.longitude}")
        }

        override suspend fun getDefaultLocation(): Flow<LocationModel> {
            return get(
                key = PREF_LAT_LNG,
                default = "$DEFAULT_LATITUDE/$DEFAULT_LONGITUDE",
            ).map { latlng ->
                val latLngList = latlng.split("/").map { it.toDouble() }
                LocationModel(latitude = latLngList[0], longitude = latLngList[1])
            }
        }

        override suspend fun getFormat(): Flow<String> {
            TODO("Not yet implemented")
        }

        override suspend fun setFormat(format: String) {
            set(key = TIME_FORMAT, value = format)
        }

        override fun getFormats(): List<String> {
            TODO("Not yet implemented")
        }

        override suspend fun setDefaultTempUnit(prefTempUnit: String) {
            set(key = PREF_TEMP_UNIT, value = prefTempUnit)
        }

        override suspend fun getDefaultTempUnit(): Flow<String> {
            return get(
                key = PREF_TEMP_UNIT,
                default = DEFAULT_TEMP_UNIT,
            )
        }

        private suspend fun <T> set(
            key: Preferences.Key<T>,
            value: T,
        ) {
            context.dataStore.edit { settings ->
                settings[key] = value
            }
        }

        private fun <T> get(
            key: Preferences.Key<T>,
            default: T,
        ): Flow<T> {
            return context.dataStore.data.map { settings ->
                settings[key] ?: default
            }
        }

        companion object {
            const val DEFAULT_LONGITUDE = 6.773456
            const val DEFAULT_LATITUDE = 51.227741
            const val DEFAULT_TEMP_UNIT = "Celsius/°C"

            const val KEY_LANGUAGE = "language"
            const val KEY_UNITS = "units"
            const val KEY_TEMP_UNIT = "temp_unit"
            const val KEY_LAT_LNG = "lat_lng"
            const val KEY_TIME_FORMAT = "time_formats"
        }
    }
