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
import org.koin.dsl.module.module

val repositoryModule = module(override = true) {
    single { createDatabaseName() }
    single { createAppDatabase(get(), get()) }
    single { createMovieDao(get()) }
    single<PrefHelper> { AppPrefs(get(), get()) }
    single { Gson() }
    single<UserRepository> { UserRepositoryImpl() }
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
}

fun createDatabaseName() = Constants.DATABASE_NAME

fun createAppDatabase(dbName: String, context: Context) = Room.databaseBuilder(context, AppDatabase::class.java, dbName).build()

fun createMovieDao(appDatabase: AppDatabase) = appDatabase.movieDao()
