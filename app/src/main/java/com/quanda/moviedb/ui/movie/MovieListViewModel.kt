package com.quanda.moviedb.ui.movie

import android.content.Context
import com.quanda.moviedb.base.viewmodel.BaseDataLoadMoreRefreshViewModel
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.source.UserRepository
import com.quanda.moviedb.data.source.remote.response.GetMovieListResponse
import io.reactivex.observers.DisposableSingleObserver

class MovieListViewModel(context: Context,
        var movieListNavigator: MovieListNavigator) : BaseDataLoadMoreRefreshViewModel<Movie>(
        context) {

    val userRepository = UserRepository.getInstance(context)

    override fun loadData(page: Int) {
        val hashMap = HashMap<String, String>()
        hashMap.put("page", page.toString())

        userRepository.getMovieList(
                hashMap).subscribe(object : DisposableSingleObserver<GetMovieListResponse>() {
            override fun onSuccess(response: GetMovieListResponse) {
                currentPage = page
                if (currentPage == 1) listItem.clear()
                if (isRefreshing.get()) resetLoadMore()
                listItem.addAll(response.results)
                onLoadSuccess(response)
            }

            override fun onError(e: Throwable) {
                onLoadFail(e)
            }
        });
    }
}