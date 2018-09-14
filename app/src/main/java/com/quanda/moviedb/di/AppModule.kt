package com.quanda.moviedb.di

import android.content.Context
import android.content.res.Resources
import com.quanda.moviedb.MainApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [
    ViewModelModule::class,
    ApiModule::class,
    RepositoryModule::class
])
class AppModule() {

    @Provides
    @Singleton
    fun provideContext(application: MainApplication): Context = application

    @Provides
    @Singleton
    fun provideResources(application: MainApplication): Resources = application.resources

}