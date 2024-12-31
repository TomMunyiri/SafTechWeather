package com.tommunyiri.saftechweather.data.sources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tommunyiri.saftechweather.data.sources.local.database.dao.WeatherDao
import com.tommunyiri.saftechweather.data.sources.local.database.entity.DBCurrentWeather
import com.tommunyiri.saftechweather.data.sources.local.database.entity.DBForecastday
import com.tommunyiri.saftechweather.data.sources.local.database.typeconverters.ListNetworkHourConverter

@Database(
    entities = [DBCurrentWeather::class, DBForecastday::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(ListNetworkHourConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract val weatherDao: WeatherDao
}
