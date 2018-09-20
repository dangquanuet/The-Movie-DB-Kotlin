package com.quanda.moviedb

import android.app.Application
import com.quanda.moviedb.di.appModule
import com.quanda.moviedb.di.networkModule
import com.quanda.moviedb.di.repositoryModule
import com.quanda.moviedb.di.viewModelModule
import org.koin.android.ext.android.startKoin

class MainApplication : Application() {

    companion object {
        var self: MainApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        self = this
        startKoin(this, listOf(
                appModule,
                networkModule,
                repositoryModule,
                viewModelModule
        ))
    }

}