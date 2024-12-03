package com.tommunyiri.saftechweather.core

import android.app.Application
import com.tommunyiri.saftechweather.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


/**
 * Created by Tom Munyiri on 02/12/2024.
 * Company:
 * Email:
 */

@HiltAndroidApp
class SafTechApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}