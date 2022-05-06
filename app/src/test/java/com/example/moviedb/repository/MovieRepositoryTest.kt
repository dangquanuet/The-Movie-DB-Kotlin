package com.example.moviedb.repository

/*
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moviedb.RxImmediateSchedulerRule
import com.example.moviedb.data.local.dao.MovieDao
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.response.GetMovieListResponse
import com.example.moviedb.data.repository.impl.UserRepositoryImpl
import com.example.moviedb.data.remote.api.ApiService
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class MovieRepositoryTest {

    lateinit var apiService: ApiService
    lateinit var movieDao: MovieDao
    lateinit var movieRepository: UserRepositoryImpl

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Before
    fun setup() {
        apiService = Mockito.mock<ApiService>(
                ApiService::class.java)
        movieDao = Mockito.mock<MovieDao>(MovieDao::class.java)
        movieRepository = UserRepositoryImpl(apiService)
    }

    @Test
    fun getMovieList() {
        //true data return
        Mockito.`when`(apiService.getMovieListAsync()).thenReturn(Single.just(createMovie()))
        movieRepository.getMovieList().test().assertValue {
            it.results.size == 2
            it.results.get(0).title == "Movie1"
            it.results.get(1).title == "Movie2"
        }

        // error data return
        val runtimeException = RuntimeException("123");
        Mockito.`when`(apiService.getMovieListAsync()).thenReturn(Single.error(runtimeException))
        movieRepository.getMovieList().test().assertError(runtimeException)
    }

    @Test
    fun testDeleteFavorite() {
        Observable.fromCallable {
            movieDao.deleteMovie("123")
        }.test()
        Mockito.verify(movieDao).deleteMovie("123")
    }

    fun createMovie(): GetMovieListResponse {
        val response = GetMovieListResponse()
        val movie1: Movie = Movie("123", title = "Movie1")
        val movie2: Movie = Movie("124", title = "Movie2")
        response.results.add(movie1)
        response.results.add(movie2)
        response.page = 1
        response.totalPages = 1
        response.totalResults = 2
        return response
    }

}*/
