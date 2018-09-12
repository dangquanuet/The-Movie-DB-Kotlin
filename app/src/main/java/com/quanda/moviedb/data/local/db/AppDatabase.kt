package com.quanda.moviedb.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.quanda.moviedb.data.local.dao.MovieDao
import com.quanda.moviedb.data.model.Movie

@Database(entities = arrayOf(Movie::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}