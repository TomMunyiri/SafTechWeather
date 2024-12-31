package com.tommunyiri.saftechweather.core.di.module

import android.content.Context
import androidx.room.Room
import com.tommunyiri.saftechweather.common.WeatherDatabasePassphrase
import com.tommunyiri.saftechweather.data.sources.local.database.WeatherDatabase
import com.tommunyiri.saftechweather.data.sources.local.database.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideWeatherDatabasePassphrase(
        @ApplicationContext context: Context,
    ) = WeatherDatabasePassphrase(context)

    @Provides
    @Singleton
    fun provideSupportFactory(weatherDatabasePassphrase: WeatherDatabasePassphrase) =
        SupportFactory(weatherDatabasePassphrase.getPassphrase())

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
        supportFactory: SupportFactory,
    ): WeatherDatabase {
        return Room.databaseBuilder(context, WeatherDatabase::class.java, "SafTechWeather.db")
            .fallbackToDestructiveMigration()
            //.openHelperFactory(supportFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherDao(database: WeatherDatabase): WeatherDao {
        return database.weatherDao
    }
}
