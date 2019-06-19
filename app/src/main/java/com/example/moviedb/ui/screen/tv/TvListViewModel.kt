package com.example.moviedb.ui.screen.tv

import com.example.moviedb.data.model.Tv
import com.example.moviedb.data.remote.ApiParams
import com.example.moviedb.data.repository.UserRepository
import com.example.moviedb.ui.base.BaseLoadMoreRefreshViewModel
import kotlinx.coroutines.launch

class TvListViewModel(
    private val userRepository: UserRepository
) : BaseLoadMoreRefreshViewModel<Tv>() {

    override fun loadData(page: Int) {
        val hashMap = HashMap<String, String>().apply {
            put(ApiParams.PAGE, page.toString())
        }

        getTv4(page, hashMap)
    }

    fun getTv4(page: Int, hashMap: HashMap<String, String>) {
        viewModelScopeExceptionHandler.launch {
            onLoadSuccess(page, userRepository.getTvList3((hashMap)).results)
        }
    }

}