package com.tommunyiri.saftechweather.data.sources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tommunyiri.saftechweather.data.sources.local.database.dao.WeatherDao
import com.tommunyiri.saftechweather.data.sources.local.database.entity.DBCurrentWeather

@Database(
    entities = [DBCurrentWeather::class],
    version = 2,
    exportSchema = true,
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract val weatherDao: WeatherDao
}
