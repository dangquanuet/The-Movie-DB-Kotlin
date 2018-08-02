package com.quanda.moviedb.ui.screen.popularmovie

import android.databinding.ObservableArrayList
import com.quanda.moviedb.data.local.dao.MovieDao
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.remote.ApiParams
import com.quanda.moviedb.data.remote.response.GetMovieListResponse
import com.quanda.moviedb.data.repository.MovieRepository
import com.quanda.moviedb.ui.base.BaseViewHolderBinding
import com.quanda.moviedb.ui.base.viewmodel.BaseLoadMoreRefreshViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PopularMovieViewModel @Inject constructor(
        val movieRepository: MovieRepository,
        val movieDao: MovieDao
) : BaseLoadMoreRefreshViewModel<Movie>() {

    var navigator: PopularMovieNavigator? = null

    var mode: Int = 0

    val itemCLickListener = object : BaseViewHolderBinding.OnItemCLickListener<Movie> {
        override fun onItemClick(position: Int, data: Movie) {
            navigator?.goToMovieDetail(data)
        }
    }

    val tempMovieList = ObservableArrayList<Movie>()

    override fun loadData(page: Int) {
        val hashMap = HashMap<String, String>()
        hashMap.put(ApiParams.PAGE, page.toString())
        when (mode) {
            PopularMovieFragment.TYPE.POPULAR.type -> hashMap.put(
                    ApiParams.SORT_BY,
                    ApiParams.POPULARITY_DESC)
            PopularMovieFragment.TYPE.TOP_RATED.type -> hashMap.put(
                    ApiParams.SORT_BY,
                    ApiParams.VOTE_AVERAGE_DESC)
            else -> hashMap.put(
                    ApiParams.SORT_BY, ApiParams.POPULARITY_DESC)
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

                listItem.addAll(response.results?.toList() ?: listOf())
                movieRepository.insertDB(response.results?.toList() ?: listOf())

                onLoadSuccess(response)
            }

            override fun onError(e: Throwable) {
                onLoadFail(e)
            }
        });
    }
}