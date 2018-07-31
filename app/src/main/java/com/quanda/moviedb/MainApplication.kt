package com.quanda.moviedb

import android.app.Activity
import android.app.Application
import com.crashlytics.android.Crashlytics
import com.quanda.moviedb.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.fabric.sdk.android.Fabric
import javax.inject.Inject

class MainApplication : Application(), HasActivityInjector {

    override fun activityInjector() = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.FLAVOR.equals("stg") || BuildConfig.FLAVOR.equals("prd")) {
            Fabric.with(this, Crashlytics())
        }

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

}