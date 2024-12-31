package com.tommunyiri.saftechweather.data.sources.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tommunyiri.saftechweather.domain.model.Astro
import com.tommunyiri.saftechweather.domain.model.Day
import com.tommunyiri.saftechweather.domain.model.Hour

@Entity(tableName = "hourly_weather_table")
data class DBForecastday(
    @Embedded
    val astro: Astro,
    @PrimaryKey
    val date: String,
    val date_epoch: Int,
    @Embedded
    val day: Day,
    @ColumnInfo(name = "weather_hourly_data")
    val hour: List<Hour>,
    @ColumnInfo(name = "modified_at")
    var modifiedAt: String? = null,
)
