package com.quanda.moviedb.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.source.local.dao.MovieDao

@Database(entities = arrayOf(Movie::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}