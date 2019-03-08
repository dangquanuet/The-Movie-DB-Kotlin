package com.example.moviedb.di

import android.content.Context
import androidx.room.Room
import com.example.moviedb.data.constants.Constants
import com.example.moviedb.data.local.db.AppDatabase
import com.example.moviedb.data.local.pref.AppPrefs
import com.example.moviedb.data.local.pref.PrefHelper
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.data.repository.UserRepository
import com.example.moviedb.data.repository.impl.MovieRepositoryImpl
import com.example.moviedb.data.repository.impl.UserRepositoryImpl
import com.google.gson.Gson
import org.koin.dsl.module

val repositoryModule = module {
    single { createDatabaseName() }
    single { createAppDatabase(get(), get()) }
    single { createMovieDao(get()) }
    single<PrefHelper> { AppPrefs(get(), get()) }
    single { Gson() }
    single<UserRepository> { UserRepositoryImpl() }
    single<MovieRepository> { MovieRepositoryImpl(get(), get(), get()) }
}

fun createDatabaseName() = Constants.DATABASE_NAME

fun createAppDatabase(dbName: String, context: Context) =
    Room.databaseBuilder(context, AppDatabase::class.java, dbName).build()

fun createMovieDao(appDatabase: AppDatabase) = appDatabase.movieDao()
