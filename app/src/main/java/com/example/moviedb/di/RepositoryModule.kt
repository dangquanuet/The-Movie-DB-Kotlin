package com.example.moviedb.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.moviedb.data.constants.Constants
import com.example.moviedb.data.local.db.AppDatabase
import com.example.moviedb.data.local.pref.AppPrefs
import com.example.moviedb.data.local.pref.PrefHelper
import com.example.moviedb.data.repository.UserRepository
import com.example.moviedb.data.repository.impl.UserRepositoryImpl
import com.google.gson.Gson
import org.koin.dsl.module

val repositoryModule = module {
    // database
    single { createAppDatabase(get()) }
    single { createMovieDao(get()) }

    // shared prefs
    single { createSharedPrefs(get()) }
    single<PrefHelper> { AppPrefs(get(), get()) }
    single { Gson() }

    // repository
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
}

fun createSharedPrefs(context: Context): SharedPreferences =
    context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

fun createAppDatabase(context: Context) =
    Room.databaseBuilder(context, AppDatabase::class.java, Constants.DATABASE_NAME).build()

fun createMovieDao(appDatabase: AppDatabase) = appDatabase.movieDao()
