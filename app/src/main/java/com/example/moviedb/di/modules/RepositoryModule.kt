package com.example.moviedb.di.modules

import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import android.content.res.Resources
import androidx.room.Room
import com.example.moviedb.data.constants.Constants
import com.example.moviedb.data.local.dao.MovieDao
import com.example.moviedb.data.local.db.AppDatabase
import com.example.moviedb.data.local.pref.AppPrefs
import com.example.moviedb.data.local.pref.PrefHelper
import com.example.moviedb.data.remote.ApiService
import com.example.moviedb.data.repository.UserRepository
import com.example.moviedb.data.repository.impl.UserRepositoryImpl
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okio.IOException
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideAppContext(@ApplicationContext context: Context): Context = context

    @Singleton
    @Provides
    fun provideResources(context: Context): Resources = context.resources

    @Singleton
    @Provides
    fun provideAssetManager(context: Context): AssetManager = context.assets

    @Singleton
    @Provides
    fun provideSharedPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, Constants.DATABASE_NAME)
            .build()

    @Singleton
    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao = appDatabase.movieDao()

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    @Singleton
    @Provides
    fun providePrefHelper(
        sharedPreferences: SharedPreferences,
        moshi: Moshi
    ): PrefHelper = AppPrefs(sharedPreferences, moshi)

    @Singleton
    @Provides
    fun provideUserRepository(
        apiService: ApiService,
        movieDao: MovieDao
    ): UserRepository = UserRepositoryImpl(apiService, movieDao)

}

val VOID_JSON_ADAPTER: Any = object : Any() {
    @FromJson
    @Throws(IOException::class)
    fun fromJson(reader: JsonReader): Void? {
        return reader.nextNull()
    }

    @ToJson
    @Throws(IOException::class)
    fun toJson(writer: JsonWriter, v: Void?) {
        writer.nullValue()
    }
}
