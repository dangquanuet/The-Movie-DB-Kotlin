package com.quanda.moviedb.di

import android.content.res.Resources
import com.quanda.moviedb.MainApplication
import org.koin.dsl.module.module

val appModule = module(override = true) {
    single { createResources(get()) }
}

fun createResources(application: MainApplication): Resources = application.resources
