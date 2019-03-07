package com.example.moviedb.di

import com.example.moviedb.data.scheduler.AppSchedulerProvider
import com.example.moviedb.data.scheduler.SchedulerProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import org.koin.experimental.builder.singleBy

val appModule = module {
    single { androidApplication().resources }
    singleBy<SchedulerProvider, AppSchedulerProvider>()
}