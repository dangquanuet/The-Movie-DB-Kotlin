package com.quanda.moviedb.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [
    ViewModelModule::class,
    ApiModule::class,
    RepositoryModule::class
])
class AppModule() {

//    @Provides
//    @Singleton
//    fun provideApplication(application: Application): Application = application

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    fun provideResources(application: Application): Resources = application.resources
}