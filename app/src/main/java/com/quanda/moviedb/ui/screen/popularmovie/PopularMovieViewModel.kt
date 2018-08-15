package com.quanda.moviedb.ui.screen.popularmovie

import com.quanda.moviedb.data.local.dao.MovieDao
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.remote.ApiParams
import com.quanda.moviedb.data.remote.response.GetMovieListResponse
import com.quanda.moviedb.data.repository.MovieRepository
import com.quanda.moviedb.ui.base.viewmodel.BaseLoadMoreRefreshViewModel
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class PopularMovieViewModel @Inject constructor(
        val movieRepository: MovieRepository,
        val movieDao: MovieDao
) : BaseLoadMoreRefreshViewModel<Movie>() {

    var navigator: PopularMovieNavigator? = null

    var mode: Int = 0

//    val tempMovieList = ObservableArrayList<Movie>()

    override fun loadData(page: Int) {
        val hashMap = HashMap<String, String>()
        hashMap.put(ApiParams.PAGE, page.toString())
        when (mode) {
            PopularMovieFragment.Type.POPULAR.type -> hashMap.put(
                    ApiParams.SORT_BY,
                    ApiParams.POPULARITY_DESC)
            PopularMovieFragment.Type.TOP_RATED.type -> hashMap.put(
                    ApiParams.SORT_BY,
                    ApiParams.VOTE_AVERAGE_DESC)
            else -> hashMap.put(
                    ApiParams.SORT_BY, ApiParams.POPULARITY_DESC)
        }

//        if (page == 1 && tempMovieList.isEmpty()) {
//            movieDao.getMoviePage(getNumberItemPerPage(), page)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(object : DisposableMaybeObserver<List<Movie>>() {
//                        override fun onComplete() {
//
//                        }
//
//                        override fun onSuccess(t: List<Movie>) {
//                            tempMovieList.addAll(t)
//
//                            val newList = listItem.value ?: ArrayList()
//                            newList.addAll(t)
//                            listItem.value = newList
//                        }
//
//                        override fun onError(e: Throwable) {
//
//                        }
//                    })
//        }

        movieRepository.getMovieList(
                hashMap).subscribe(object : DisposableSingleObserver<GetMovieListResponse>() {
            override fun onSuccess(response: GetMovieListResponse) {
                currentPage.value = page
                if (currentPage.value == 1) listItem.value?.clear()
                if (isRefreshing.value == true) resetLoadMore()

//                val newList = listItem.value ?: ArrayList()
//                newList.removeAll(tempMovieList)
//                listItem.value = newList
//                tempMovieList.clear()

                val newList2 = listItem.value ?: ArrayList()
                newList2.addAll(response.results?.toList() ?: listOf())
                listItem.value = newList2
                movieRepository.insertDB(response.results?.toList() ?: listOf())

                onLoadSuccess(response.results?.size ?: 0)
            }

            override fun onError(e: Throwable) {
                onLoadFail(e)
            }
        });
    }
}