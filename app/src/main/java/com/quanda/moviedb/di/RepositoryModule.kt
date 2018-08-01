package com.quanda.moviedb.di

import android.arch.persistence.room.Room
import android.content.Context
import com.google.gson.Gson
import com.quanda.moviedb.data.constants.Constants
import com.quanda.moviedb.data.local.db.AppDatabase
import com.quanda.moviedb.data.local.pref.AppPrefs
import com.quanda.moviedb.data.local.pref.PrefHelper
import com.quanda.moviedb.data.repository.MovieRepository
import com.quanda.moviedb.data.repository.UserRepository
import com.quanda.moviedb.data.repository.impl.MovieRepositoryImpl
import com.quanda.moviedb.data.repository.impl.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Named("database_name")
    fun providerDatabaseName(): String {
        return Constants.DATABASE_NAME
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@Named("database_name") dbName: String, context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java,
                dbName).fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    fun providePrefHelper(appPrefs: AppPrefs): PrefHelper {
        return appPrefs
    }

    @Provides
    @Singleton
    fun providerAppPrefs(context: Context, gson: Gson): AppPrefs {
        return AppPrefs(context, gson)
    }

    @Provides
    @Singleton
    fun providerGSon(): Gson = Gson()

    @Provides
    @Singleton
    fun providerUserRepository(repository: UserRepositoryImpl): UserRepository = repository

    @Provides
    @Singleton
    fun providerMovieRepository(repository: MovieRepositoryImpl): MovieRepository = repository

}
