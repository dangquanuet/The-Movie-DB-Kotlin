package com.quanda.moviedb.ui.screen.favoritemovie

import com.quanda.moviedb.data.local.dao.MovieDao
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.ui.base.BaseLoadMoreRefreshViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FavoriteMovieViewModel constructor(
        private val movieDao: MovieDao
) : BaseLoadMoreRefreshViewModel<Movie>() {

    override fun loadData(page: Int) {
        addDisposable(movieDao.getFavorite(getNumberItemPerPage(), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onLoadSuccess(page, it)
                }, {
                    onLoadFail(it)
                }))
    }

}