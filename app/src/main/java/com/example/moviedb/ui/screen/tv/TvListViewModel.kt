package com.example.moviedb.ui.screen.tv

import com.example.moviedb.data.model.Tv
import com.example.moviedb.data.remote.ApiParams
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.ui.base.BaseLoadMoreRefreshViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class TvListViewModel constructor(
    private val movieRepository: MovieRepository
) : BaseLoadMoreRefreshViewModel<Tv>() {

    override fun loadData(page: Int) {
        val hashMap = HashMap<String, String>()
        hashMap.put(ApiParams.PAGE, page.toString())

        GlobalScope.async {
            try {
                onLoadSuccess(page, movieRepository.getTvList(hashMap).await().results)
            } catch (e: Throwable) {
                onLoadFail(e)
            }
        }

        /*
        movieRepository.getTvList(hashMap, {
            onLoadSuccess(page, it.results)
        }, { e ->
            onLoadFail(e)
        })
        */

        /*
        val result = movieRepository.getTvList2(hashMap)
        when (result) {
            is Result.Success<GetTvListResponse> -> {
                val response = result.data
                currentPage = page
                if (currentPage == 1) listItem.clear()
                if (isRefreshing.value == true) resetLoadMore()
                listItem.addAll(response.results)
                onLoadSuccess(page, response)
            }
            is Result.Error -> {
                onLoadFail(result.error)
            }
        }
        */
    }

}