package com.aos.myapplication

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AppApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.Forest.plant(Timber.DebugTree())
    }

}