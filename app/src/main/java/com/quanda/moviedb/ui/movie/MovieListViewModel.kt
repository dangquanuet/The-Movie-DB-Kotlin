package com.quanda.moviedb.ui.movie

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.quanda.moviedb.MovieDBApplication
import com.quanda.moviedb.base.viewmodel.BaseDataLoadMoreRefreshViewModel
import com.quanda.moviedb.constants.ApiParam
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.source.UserRepository
import com.quanda.moviedb.data.source.remote.response.GetMovieListResponse
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class MovieListViewModel(application: Application,
        val movieListNavigator: MovieListNavigator) : BaseDataLoadMoreRefreshViewModel<Movie>(
        application) {

    class CustomFactory(val application: Application,
            val movieListNavigator: MovieListNavigator) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MovieListViewModel(application, movieListNavigator) as T
        }
    }

    @Inject
    lateinit var userRepository: UserRepository

    init {
        (application as MovieDBApplication).appComponent.inject(this)
    }

    override fun loadData(page: Int) {
        val hashMap = HashMap<String, String>()
        hashMap.put(ApiParam.PAGE, page.toString())

        userRepository.getMovieList(
                hashMap).subscribe(object : DisposableSingleObserver<GetMovieListResponse>() {
            override fun onSuccess(response: GetMovieListResponse) {
                currentPage = page
                if (currentPage == 1) listItem.clear()
                if (isRefreshing.value == true) resetLoadMore()
                listItem.addAll(response.results)
                onLoadSuccess(response)
            }

            override fun onError(e: Throwable) {
                onLoadFail(e)
            }
        });
    }
}