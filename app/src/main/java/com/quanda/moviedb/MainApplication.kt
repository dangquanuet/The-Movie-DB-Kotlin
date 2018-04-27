package com.quanda.moviedb

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.quanda.moviedb.di.AppComponent
import com.quanda.moviedb.di.AppModule
import com.quanda.moviedb.di.DaggerAppComponent
import io.fabric.sdk.android.Fabric

class MainApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.FLAVOR.equals("stg") || BuildConfig.FLAVOR.equals("prd")) {
            Fabric.with(this, Crashlytics())
        }

        appComponent = createAppComponent()
    }

    private fun createAppComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .appModule(AppModule(this@MainApplication))
                .build()
    }
}