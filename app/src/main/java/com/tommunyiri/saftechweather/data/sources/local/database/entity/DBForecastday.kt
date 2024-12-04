package com.tommunyiri.saftechweather.data.sources.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Embedded
import com.tommunyiri.saftechweather.domain.model.Astro
import com.tommunyiri.saftechweather.domain.model.Day
import com.tommunyiri.saftechweather.domain.model.Hour

@Entity(tableName = "hourly_weather_table")
data class DBForecastday(
    val astro: Astro,
    @PrimaryKey
    val date: String,
    val date_epoch: Int,
    @Embedded
    val day: Day,
    val hour: List<Hour>
)