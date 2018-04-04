package com.quanda.moviedb.ui.movie

import android.content.Context
import com.quanda.moviedb.base.viewmodel.BaseDataLoadMoreRefreshViewModel
import com.quanda.moviedb.data.model.Movie

class MovieListViewModel(context: Context,
        movieListNavigator: MovieListNavigator) : BaseDataLoadMoreRefreshViewModel<Movie>(context,
        movieListNavigator) {

    override fun loadData(page: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}