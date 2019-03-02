package com.example.moviedb.di

import com.example.moviedb.data.flow.AppSchedulerProvider
import com.example.moviedb.data.flow.SchedulerProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import org.koin.experimental.builder.singleBy

val appModule = module {
    single { androidApplication().resources }
    singleBy<SchedulerProvider, AppSchedulerProvider>()
}