package com.example.moviedb

import android.app.Application
import androidx.multidex.MultiDex
import com.example.moviedb.di.appModules
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        MultiDex.install(this)

        startKoin {
            androidContext(this@MainApplication)
            modules(appModules)
        }

        if (BuildConfig.DEBUG) {
            // init timber
            Timber.plant(Timber.DebugTree())

            // init stetho
            Stetho.initializeWithDefaults(this)
        }
    }

}