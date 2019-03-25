package com.example.moviedb.ui.screen.popularmovie

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import com.example.moviedb.data.constants.MovieListType
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.ApiParams
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.ui.base.BaseLoadMoreRefreshViewModel
import kotlinx.coroutines.launch


class PopularMovieViewModel constructor(
    val resources: Resources,
    val movieRepository: MovieRepository
) : BaseLoadMoreRefreshViewModel<Movie>() {

    var mode = MutableLiveData<Int>().apply { value = MovieListType.POPULAR.type }

    override fun loadData(page: Int) {
        val hashMap = HashMap<String, String>()
        hashMap.put(ApiParams.PAGE, page.toString())
        when (mode.value) {
            MovieListType.POPULAR.type -> hashMap.put(
                ApiParams.SORT_BY,
                ApiParams.POPULARITY_DESC
            )
            MovieListType.TOP_RATED.type -> hashMap.put(
                ApiParams.SORT_BY,
                ApiParams.VOTE_AVERAGE_DESC
            )
            else -> hashMap.put(
                ApiParams.SORT_BY, ApiParams.POPULARITY_DESC
            )
        }

        ioScope.launch {
            try {
                onLoadSuccess(page, movieRepository.getMovieList(hashMap).results)
            } catch (e: Exception) {
                onLoadFail(e)
            }
        }
    }
}