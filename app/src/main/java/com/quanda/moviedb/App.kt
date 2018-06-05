package com.quanda.moviedb

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.quanda.moviedb.di.*
import io.fabric.sdk.android.Fabric

class App : Application() {

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
                .appModule(AppModule(this@App))
                .apiModule(ApiModule())
                .roomModule(RoomModule(this@App))
                .build()
    }
}