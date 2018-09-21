package com.quanda.moviedb.ui.screen.movie

import com.quanda.moviedb.MainApplication
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.remote.ApiParams
import com.quanda.moviedb.data.repository.MovieRepository
import com.quanda.moviedb.ui.base.BaseLoadMoreRefreshViewModel

class MovieListViewModel constructor(
        private val movieRepository: MovieRepository,
        private val mainApplication: MainApplication
) : BaseLoadMoreRefreshViewModel<Movie>() {

    override fun loadData(page: Int) {
        val hashMap = HashMap<String, String>()
        hashMap.put(ApiParams.PAGE, page.toString())

        movieRepository.getMovieList(hashMap)
                .subscribe({
                    currentPage.value = page
                    if (currentPage.value == 1) listItem.value?.clear()
                    if (isRefreshing.value == true) resetLoadMore()
                    val newList = listItem.value ?: arrayListOf()
                    newList.addAll(it.results ?: arrayListOf())
                    listItem.value = newList
                    onLoadSuccess(it.results?.size ?: 0)
                }, {
                    onLoadFail(it)
                })
    }

}