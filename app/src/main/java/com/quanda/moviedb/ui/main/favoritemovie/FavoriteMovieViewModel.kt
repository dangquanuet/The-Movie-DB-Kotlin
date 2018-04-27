package com.quanda.moviedb.ui.main.favoritemovie

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.quanda.moviedb.MovieDBApplication
import com.quanda.moviedb.base.BaseViewHolderBinding
import com.quanda.moviedb.base.viewmodel.BaseDataLoadMoreRefreshViewModel
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.source.local.dao.MovieDao
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavoriteMovieViewModel(application: Application,
        val favoriteMovieNavigator: FavoriteMovieNavigator) : BaseDataLoadMoreRefreshViewModel<Movie>(
        application) {

    class CustomFactory(val application: Application,
            val favoriteMovieNavigator: FavoriteMovieNavigator) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FavoriteMovieViewModel(application, favoriteMovieNavigator) as T
        }
    }

    @Inject
    lateinit var movieDao: MovieDao

    val itemCLickListener = object : BaseViewHolderBinding.OnItemCLickListener<Movie> {
        override fun onItemClick(position: Int, data: Movie) {
            favoriteMovieNavigator.goToMovieDetailWithResult(data)
        }
    }

    init {
        (application as MovieDBApplication).appComponent.inject(this)
    }

    override fun loadData(page: Int) {
        movieDao.getFavorite(getNumberItemPerPage(), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DisposableMaybeObserver<List<Movie>>() {
                    override fun onSuccess(t: List<Movie>) {
                        currentPage = page
                        if (currentPage == 1) listItem.clear()
                        if (isRefreshing.value == true) resetLoadMore()
                        listItem.addAll(t)

                        isLastPage == t.size < getNumberItemPerPage()
                        isDataLoading.value = false
                        isRefreshing.value = false
                        isDataLoadMore.value = false
                    }

                    override fun onComplete() {

                    }

                    override fun onError(e: Throwable) {
                        onLoadFail(e)
                    }
                })
    }
}