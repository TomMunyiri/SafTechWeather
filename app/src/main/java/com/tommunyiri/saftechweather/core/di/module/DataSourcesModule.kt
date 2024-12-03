package com.tommunyiri.saftechweather.core.di.module

import com.tommunyiri.saftechweather.data.sources.local.database.WeatherLocalDataSource
import com.tommunyiri.saftechweather.data.sources.local.database.WeatherLocalDataSourceImpl
import com.tommunyiri.saftechweather.data.sources.remote.WeatherRemoteDataSource
import com.tommunyiri.saftechweather.data.sources.remote.WeatherRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourcesModule {
    @Binds
    abstract fun bindLocalDataSource(localDataSourceImpl: WeatherLocalDataSourceImpl): WeatherLocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: WeatherRemoteDataSourceImpl): WeatherRemoteDataSource
}
