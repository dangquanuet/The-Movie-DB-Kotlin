package com.quanda.moviedb.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.quanda.moviedb.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    private val context: Context

    init {
        context = app.applicationContext
    }

    @Provides
    @Singleton
    fun provideApp(): App = app

    @Provides
    @Singleton
    fun provideApplication(): Application = app

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideResources(): Resources = context.resources
}