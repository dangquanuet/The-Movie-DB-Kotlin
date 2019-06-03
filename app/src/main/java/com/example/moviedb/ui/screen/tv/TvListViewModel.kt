package com.example.moviedb.ui.screen.tv

import com.example.moviedb.data.model.Tv
import com.example.moviedb.data.remote.ApiParams
import com.example.moviedb.data.remote.response.GetTvListResponse
import com.example.moviedb.data.remote.response.Result
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.ui.base.BaseLoadMoreRefreshViewModel
import kotlinx.coroutines.launch

class TvListViewModel(
    private val movieRepository: MovieRepository
) : BaseLoadMoreRefreshViewModel<Tv>() {

    override fun loadData(page: Int) {
        val hashMap = HashMap<String, String>().apply {
            put(ApiParams.PAGE, page.toString())
        }

        getTv4(page, hashMap)
    }

    /*fun getTv(page: Int, hashMap: HashMap<String, String>) {
        movieRepository.getTvList(hashMap, {
            onLoadSuccess(page, it.results)
        }, {
            onLoadFail(it)
        })
    }

    fun getTv2(page: Int, hashMap: HashMap<String, String>) {
        ioScope.launch {
            val result = movieRepository.getTvList2(hashMap)
            when (result) {
                is Result.Success<GetTvListResponse> -> {
                    onLoadSuccess(page, result.data.results)
                }
                is Result.Error -> {
                    onLoadFail(result.error)
                }
            }
        }
    }

    fun getTv3(page: Int, hashMap: HashMap<String, String>) {
        uiScope.launch {
            try {
                onLoadSuccess(page, movieRepository.getTvList3((hashMap)).results)
            } catch (e: Exception) {
                onLoadFail(e)
            }
        }
    }*/

    fun getTv4(page: Int, hashMap: HashMap<String, String>) {
        uiScopeError.launch {
            onLoadSuccess(page, movieRepository.getTvList3((hashMap)).results)
        }
    }

}