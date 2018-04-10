package com.quanda.moviedb.ui.main.popularmovie

import android.content.Context
import com.quanda.moviedb.base.BaseViewHolderBinding
import com.quanda.moviedb.base.viewmodel.BaseDataLoadMoreRefreshViewModel
import com.quanda.moviedb.constants.ApiParam
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.source.UserRepository
import com.quanda.moviedb.data.source.remote.response.GetMovieListResponse
import io.reactivex.observers.DisposableSingleObserver

class PopularMovieViewModel(context: Context,
        val popularMovieNavigator: PopularMovieNavigator,
        val mode: Int) : BaseDataLoadMoreRefreshViewModel<Movie>(
        context, popularMovieNavigator) {

    val userRepository = UserRepository.getInstance()
    val itemCLickListener = object : BaseViewHolderBinding.OnItemCLickListener<Movie> {
        override fun onItemClick(position: Int, data: Movie) {
            popularMovieNavigator.goToMovieDetail(data)
        }
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

        userRepository.getMovieList(
                hashMap).subscribe(object : DisposableSingleObserver<GetMovieListResponse>() {
            override fun onSuccess(response: GetMovieListResponse) {
                currentPage = page
                if (currentPage == 1) listItem.clear()
                if (isRefreshing.get()) resetLoadMore()
                listItem.addAll(response.results)

//                MovieDBApplication.database?.movieDao()?.insertMovieList(response.results)

                onLoadSuccess(response)
            }

            override fun onError(e: Throwable) {
                onLoadFail(e)
            }
        });
    }
}