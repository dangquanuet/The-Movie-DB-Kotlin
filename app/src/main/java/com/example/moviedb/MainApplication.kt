package com.example.moviedb

import android.app.Application
import com.example.moviedb.di.appModule
import com.example.moviedb.di.networkModule
import com.example.moviedb.di.repositoryModule
import com.example.moviedb.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(appModule, networkModule, repositoryModule, viewModelModule)
        }
    }

}