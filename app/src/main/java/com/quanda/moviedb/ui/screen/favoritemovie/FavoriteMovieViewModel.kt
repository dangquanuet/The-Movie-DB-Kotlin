package com.quanda.moviedb.ui.screen.favoritemovie

import com.quanda.moviedb.data.local.dao.MovieDao
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.ui.base.viewmodel.BaseLoadMoreRefreshViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavoriteMovieViewModel @Inject constructor(
        private val movieDao: MovieDao
) : BaseLoadMoreRefreshViewModel<Movie>() {

    override fun loadData(page: Int) {
        movieDao.getFavorite(getNumberItemPerPage(), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DisposableMaybeObserver<List<Movie>>() {
                    override fun onSuccess(t: List<Movie>) {
                        currentPage.value = page
                        if (currentPage.value == 1) listItem.value = null
                        if (isRefreshing.value == true) resetLoadMore()
                        val newList = if (listItem.value != null) listItem.value else ArrayList()
                        newList?.addAll(t)
                        listItem.value = newList
                        onLoadSuccess(t.size)
                    }

                    override fun onComplete() {
                        onLoadSuccess(0)
                    }

                    override fun onError(e: Throwable) {
                        onLoadFail(e)
                    }
                })
    }
}