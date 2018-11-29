package com.example.moviedb

import android.app.Application
import com.example.moviedb.di.modules
import org.koin.android.ext.android.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, modules)
    }

}