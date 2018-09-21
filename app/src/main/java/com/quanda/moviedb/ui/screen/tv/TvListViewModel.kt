package com.quanda.moviedb.ui.screen.tv

import com.quanda.moviedb.data.model.Tv
import com.quanda.moviedb.data.remote.ApiParams
import com.quanda.moviedb.data.repository.MovieRepository
import com.quanda.moviedb.ui.base.BaseLoadMoreRefreshViewModel
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class TvListViewModel constructor(
        private val movieRepository: MovieRepository
) : BaseLoadMoreRefreshViewModel<Tv>() {

    override fun loadData(page: Int) {
        val hashMap = HashMap<String, String>()
        hashMap.put(ApiParams.PAGE, page.toString())

        async(UI) {
            try {
                val response = movieRepository.getTvList(hashMap).await()
                currentPage.value = page
                if (currentPage.value == 1) listItem.value?.clear()
                if (isRefreshing.value == true) resetLoadMore()
                val newList = response.results ?: ArrayList()
                newList.addAll(response.results ?: listOf())
                listItem.value = newList
                onLoadSuccess(response.results?.size ?: 0)
            } catch (e: Throwable) {
                onLoadFail(e)
            }
        }

        /*
        movieRepository.getTvList(hashMap, { response ->
            currentPage = page
            if (currentPage == 1) listItem.clear()
            if (isRefreshing.value == true) resetLoadMore()
            listItem.addAll(response.results)
            onLoadSuccess(response)
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
                onLoadSuccess(response)
            }
            is Result.Error -> {
                onLoadFail(result.error)
            }
        }
        */
    }

}