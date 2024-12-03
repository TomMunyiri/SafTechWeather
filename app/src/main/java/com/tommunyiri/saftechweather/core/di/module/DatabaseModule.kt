package com.tommunyiri.saftechweather.core.di.module

import android.content.Context
import androidx.room.Room
import com.tommunyiri.saftechweather.data.sources.local.database.WeatherDatabase
import com.tommunyiri.saftechweather.data.sources.local.database.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(context: Context): WeatherDatabase {
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "SafTechWeather.db",
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideWeatherDao(database: WeatherDatabase): WeatherDao {
        return database.weatherDao
    }
}
