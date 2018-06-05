package com.quanda.moviedb.di

import android.app.Application
import android.content.Context
import com.quanda.moviedb.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(val application: Application) {

    private val context: Context

    init {
        context = application
    }

    @Provides
    @Singleton
    fun provideAppDatabase() = AppDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideMovieDao() = provideAppDatabase().movieDao()

}