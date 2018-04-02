package com.quanda.moviedb

import android.app.Application
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

class MovieDBApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.FLAVOR.equals("stg") || BuildConfig.FLAVOR.equals("prd")) {
            Fabric.with(this, Crashlytics())
        }
    }
}