package com.quanda.moviedb.data.room

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.local.dao.MovieDao
import com.quanda.moviedb.data.local.db.AppDatabase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    companion object {
        val movie1 = Movie("1234", vote_average = 4.6)
        val movie2 = Movie("2345", vote_average = 3.7)
    }

    lateinit var database: AppDatabase
    lateinit var movieDao: MovieDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase::class.java).build()
        movieDao = database.movieDao()

    }

    @After
    fun close() {
        database.close()
    }

    @Test
    fun getMovieWhenInserted() {
        movieDao.getMovieList().test().assertValue {
            it.size == 0
        }

        movieDao.getMovie("123").test().assertNoValues()
    }

    @Test
    fun insertAndGetMovie() {
        movieDao.insert(movie1)
        movieDao.insert(movie2)

        movieDao.getMovieList().test().assertValue {
            it.size == 2
        }

        movieDao.getMovie(movie1.id).test().assertValue {
            it.id == movie1.id && it.vote_average == movie1.vote_average
        }

        movieDao.getMovie(movie2.id).test().assertValue {
            it.id == movie2.id && it.vote_average == movie2.vote_average
        }
    }

    @Test
    fun deleteMovie() {
        movieDao.insert(movie1)
        movieDao.insert(movie2)
        movieDao.getMovieList().test().assertValue {
            it.size == 2
        }

        movieDao.deleteMovie(movie1.id)
        movieDao.getMovieList().test().assertValue {
            it.size == 1
        }

        movieDao.deleteMovie(movie2.id)
        movieDao.getMovieList().test().assertValue {
            it.size == 0
        }
    }

}