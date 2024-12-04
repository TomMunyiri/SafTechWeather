package com.tommunyiri.saftechweather.data.sources.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tommunyiri.saftechweather.data.sources.local.database.entity.DBCurrentWeather

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(vararg dbCurrentWeather: DBCurrentWeather)

    @Query("SELECT * FROM weather_table ORDER BY unique_id DESC LIMIT 1")
    suspend fun getWeather(): DBCurrentWeather

    @Query("SELECT * FROM weather_table ORDER BY unique_id DESC")
    suspend fun getAllWeather(): List<DBCurrentWeather>

    @Query("DELETE FROM weather_table")
    suspend fun deleteAllWeather()

}