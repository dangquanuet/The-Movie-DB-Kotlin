package com.quanda.moviedb.ui.main.popularmovie

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.databinding.ObservableArrayList
import com.quanda.moviedb.MovieDBApplication
import com.quanda.moviedb.base.BaseViewHolderBinding
import com.quanda.moviedb.base.viewmodel.BaseDataLoadMoreRefreshViewModel
import com.quanda.moviedb.constants.ApiParam
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.source.UserRepository
import com.quanda.moviedb.data.source.local.AppDatabase
import com.quanda.moviedb.data.source.local.dao.MovieDao
import com.quanda.moviedb.data.source.remote.response.GetMovieListResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
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
    lateinit var userRepository: UserRepository

    val itemCLickListener = object : BaseViewHolderBinding.OnItemCLickListener<Movie> {
        override fun onItemClick(position: Int, data: Movie) {
            popularMovieNavigator.goToMovieDetail(data)
        }
    }

    @Inject
    lateinit var appDatabase: AppDatabase

    val movieDao: MovieDao

    val tempMovieList = ObservableArrayList<Movie>()

    init {
        (application as MovieDBApplication).appComponent.inject(this)
        movieDao = appDatabase.movieDao()
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
            movieDao.getMoviePage(getNumberItemPerPage(), page).subscribeOn(
                    Schedulers.io()).observeOn(
                    AndroidSchedulers.mainThread()).subscribe(object : DisposableSingleObserver<List<Movie>>() {
                override fun onSuccess(t: List<Movie>) {
                    tempMovieList.addAll(t)
                    listItem.addAll(t)
                }

                override fun onError(e: Throwable) {

                }
            })
        }

        userRepository.getMovieList(
                hashMap).subscribe(object : DisposableSingleObserver<GetMovieListResponse>() {
            override fun onSuccess(response: GetMovieListResponse) {
                currentPage = page
                if (currentPage == 1) listItem.clear()
                if (isRefreshing.value == true) {
                    resetLoadMore()
                    launch {
                        async {
                            movieDao.deleteAll()
                        }
                    }
                }

                listItem.removeAll(tempMovieList)
                tempMovieList.clear()

                listItem.addAll(response.results)
                launch {
                    async {
                        movieDao.insert(response.results)
                    }
                }

                onLoadSuccess(response)
            }

            override fun onError(e: Throwable) {
                onLoadFail(e)
            }
        });
    }
}