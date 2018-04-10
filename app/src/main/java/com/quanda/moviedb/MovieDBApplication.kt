package com.quanda.moviedb

import android.app.Application
import android.arch.persistence.room.Room
import com.crashlytics.android.Crashlytics
import com.quanda.moviedb.data.source.local.AppDatabase
import io.fabric.sdk.android.Fabric

class MovieDBApplication : Application() {

    companion object {
        var database: AppDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.FLAVOR.equals("stg") || BuildConfig.FLAVOR.equals("prd")) {
            Fabric.with(this, Crashlytics())
        }

        MovieDBApplication.database = Room.databaseBuilder(this, AppDatabase::class.java,
                "app-database").build()
    }

}