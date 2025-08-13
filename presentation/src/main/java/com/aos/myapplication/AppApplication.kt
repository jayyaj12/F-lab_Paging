package com.aos.myapplication

import android.app.Application
import com.aos.data.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AppApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.Forest.plant(Timber.DebugTree())
        }
    }

}