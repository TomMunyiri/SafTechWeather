package com.tommunyiri.saftechweather.data.sources.local.database.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tommunyiri.saftechweather.domain.model.Hour
import java.lang.reflect.Type

class ListNetworkHourConverter {
    val gson = Gson()

    val type: Type = object : TypeToken<List<Hour?>?>() {}.type

    /**
     * Converts a listOf[Hour] to a [String]
     */
    @TypeConverter
    fun fromWeatherDtoList(list: List<Hour?>?): String {
        return gson.toJson(list, type)
    }

    /**
     * Converts a [String] to a listOf[Hour]
     */
    @TypeConverter
    fun toWeatherDtoList(json: String?): List<Hour> {
        return gson.fromJson(json, type)
    }
}
