package com.quanda.moviedb.ui.screen.main.favoritemovie

import com.quanda.moviedb.App
import com.quanda.moviedb.data.local.dao.MovieDao
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.ui.base.BaseViewHolderBinding
import com.quanda.moviedb.ui.base.viewmodel.BaseDataLoadMoreRefreshViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavoriteMovieViewModel : BaseDataLoadMoreRefreshViewModel<Movie>() {

    @Inject
    lateinit var movieDao: MovieDao

    lateinit var navigator: FavoriteMovieNavigator

    val itemCLickListener = object : BaseViewHolderBinding.OnItemCLickListener<Movie> {
        override fun onItemClick(position: Int, data: Movie) {
            navigator.goToMovieDetailWithResult(data)
        }
    }

    init {
        App.appComponent.inject(this)
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