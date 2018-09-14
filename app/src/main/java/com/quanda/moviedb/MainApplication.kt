package com.quanda.moviedb

import com.quanda.moviedb.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


class MainApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out MainApplication> {
        return DaggerAppComponent.builder().create(this)
    }

}