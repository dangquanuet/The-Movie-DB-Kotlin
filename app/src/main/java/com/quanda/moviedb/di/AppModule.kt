package com.quanda.moviedb.di

import android.app.Application
import com.quanda.moviedb.data.source.UserRepository
import com.quanda.moviedb.data.source.local.AppDatabase
import com.quanda.moviedb.data.source.local.SharedPreferenceApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideContext() = application

    @Provides
    @Singleton
    fun provideResources() = application.resources

    @Provides
    @Singleton
    fun provideSharePreferenceApi() = SharedPreferenceApi.getInstance(application)

    @Provides
    @Singleton
    fun provideUserRepository() = UserRepository.getInstance(application)

    @Provides
    @Singleton
    fun provideAppDatabase() = AppDatabase.getInstance(application)
}