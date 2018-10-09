package com.quanda.moviedb.ui.screen.popularmovie

import androidx.lifecycle.MutableLiveData
import com.quanda.moviedb.data.constants.MovieListType
import com.quanda.moviedb.data.local.dao.MovieDao
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.remote.ApiParams
import com.quanda.moviedb.data.repository.MovieRepository
import com.quanda.moviedb.ui.base.BaseLoadMoreRefreshViewModel


class PopularMovieViewModel constructor(
        val movieRepository: MovieRepository,
        val movieDao: MovieDao
) : BaseLoadMoreRefreshViewModel<Movie>() {

    var mode = MutableLiveData<Int>().apply { value = MovieListType.POPULAR.type }

    override fun loadData(page: Int) {
        val hashMap = HashMap<String, String>()
        hashMap.put(ApiParams.PAGE, page.toString())
        when (mode.value) {
            MovieListType.POPULAR.type -> hashMap.put(
                    ApiParams.SORT_BY,
                    ApiParams.POPULARITY_DESC)
            MovieListType.TOP_RATED.type -> hashMap.put(
                    ApiParams.SORT_BY,
                    ApiParams.VOTE_AVERAGE_DESC)
            else -> hashMap.put(
                    ApiParams.SORT_BY, ApiParams.POPULARITY_DESC)
        }

        addDisposable(movieRepository.getMovieList(hashMap)
                .subscribe({
                    onLoadSuccess(page, it.results)
                }, {
                    onLoadFail(it)
                }))
    }
}