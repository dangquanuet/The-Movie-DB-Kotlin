package com.quanda.moviedb.ui.screen.main.popularmovie

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.databinding.ObservableArrayList
import com.quanda.moviedb.MainApplication
import com.quanda.moviedb.data.constants.ApiParam
import com.quanda.moviedb.data.local.dao.MovieDao
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.remote.response.GetMovieListResponse
import com.quanda.moviedb.data.repository.impl.MovieRepository
import com.quanda.moviedb.ui.base.BaseViewHolderBinding
import com.quanda.moviedb.ui.base.viewmodel.BaseDataLoadMoreRefreshViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PopularMovieViewModel(application: Application,
        val popularMovieNavigator: PopularMovieNavigator,
        val mode: Int) : BaseDataLoadMoreRefreshViewModel<Movie>(application) {

    class CustomFactory(val application: Application,
            val popularMovieNavigator: PopularMovieNavigator,
            val mode: Int) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PopularMovieViewModel(application, popularMovieNavigator, mode) as T
        }
    }

    @Inject
    lateinit var movieRepository: MovieRepository
    @Inject
    lateinit var movieDao: MovieDao

    val itemCLickListener = object : BaseViewHolderBinding.OnItemCLickListener<Movie> {
        override fun onItemClick(position: Int, data: Movie) {
            popularMovieNavigator.goToMovieDetail(data)
        }
    }

    val tempMovieList = ObservableArrayList<Movie>()

    init {
        MainApplication.appComponent.inject(this)
    }

    override fun loadData(page: Int) {
        val hashMap = HashMap<String, String>()
        hashMap.put(ApiParam.PAGE, page.toString())
        when (mode) {
            PopularMovieFragment.TYPE.POPULAR.type -> hashMap.put(ApiParam.SORT_BY,
                    ApiParam.POPULARITY_DESC)
            PopularMovieFragment.TYPE.TOP_RATED.type -> hashMap.put(ApiParam.SORT_BY,
                    ApiParam.VOTE_AVERAGE_DESC)
            else -> hashMap.put(ApiParam.SORT_BY, ApiParam.POPULARITY_DESC)
        }

        if (page == 1 && tempMovieList.isEmpty()) {
            movieDao.getMoviePage(getNumberItemPerPage(), page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : DisposableMaybeObserver<List<Movie>>() {
                        override fun onComplete() {

                        }

                        override fun onSuccess(t: List<Movie>) {
                            tempMovieList.addAll(t)
                            listItem.addAll(t)
                        }

                        override fun onError(e: Throwable) {

                        }
                    })
        }

        movieRepository.getMovieList(
                hashMap).subscribe(object : DisposableSingleObserver<GetMovieListResponse>() {
            override fun onSuccess(response: GetMovieListResponse) {
                currentPage = page
                if (currentPage == 1) listItem.clear()
                if (isRefreshing.value == true) {
                    resetLoadMore()
                }

                listItem.removeAll(tempMovieList)
                tempMovieList.clear()

                listItem.addAll(response.results)
                movieRepository.insertDB(response.results)

                onLoadSuccess(response)
            }

            override fun onError(e: Throwable) {
                onLoadFail(e)
            }
        });
    }
}