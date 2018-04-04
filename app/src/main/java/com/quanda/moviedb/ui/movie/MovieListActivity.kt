package com.quanda.moviedb.ui.movie

import com.quanda.moviedb.base.activity.BaseDataLoadMoreRefreshActivity
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.databinding.ActivityBaseLoadmoreRefreshBinding

class MovieListActivity : BaseDataLoadMoreRefreshActivity<ActivityBaseLoadmoreRefreshBinding, MovieListViewModel, Movie>(), MovieListNavigator {

    override fun initViewModel(): MovieListViewModel {
        return MovieListViewModel(this, this)
    }

    override fun initRecyclerView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}