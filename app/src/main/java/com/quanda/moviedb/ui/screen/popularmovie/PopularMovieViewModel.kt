package com.quanda.moviedb.ui.screen.popularmovie

import android.arch.lifecycle.MutableLiveData
import com.quanda.moviedb.data.constants.MovieListType
import com.quanda.moviedb.data.local.dao.MovieDao
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.remote.ApiParams
import com.quanda.moviedb.data.repository.MovieRepository
import com.quanda.moviedb.ui.base.BaseLoadMoreRefreshViewModel


class PopularMovieViewModel constructor(
        val movieRepository: MovieRepository,
        val movieDao: MovieDao
) : BaseLoadMoreRefreshViewModel<Movie>() {

    var mode = MutableLiveData<Int>().apply { value = MovieListType.POPULAR.type }

//    val tempMovieList = ObservableArrayList<Movie>()

    override fun loadData(page: Int) {
        val hashMap = HashMap<String, String>()
        hashMap.put(ApiParams.PAGE, page.toString())
        when (mode.value) {
            MovieListType.POPULAR.type -> hashMap.put(
                    ApiParams.SORT_BY,
                    ApiParams.POPULARITY_DESC)
            MovieListType.TOP_RATED.type -> hashMap.put(
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

        movieRepository.getMovieList(hashMap)
                .subscribe({
                    currentPage.value = page
                    if (currentPage.value == 1) listItem.value?.clear()
                    if (isRefreshing.value == true) resetLoadMore()

//                val newList = listItem.value ?: ArrayList()
//                newList.removeAll(tempMovieList)
//                listItem.value = newList
//                tempMovieList.clear()

                    val newList2 = listItem.value ?: ArrayList()
                    newList2.addAll(it.results ?: listOf())
                    listItem.value = newList2
                    movieRepository.insertDB(it.results ?: listOf())

                    onLoadSuccess(it.results?.size ?: 0)
                }, {
                    onLoadFail(it)
                })
    }
}