package com.quanda.moviedb.ui.screen.tv

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.quanda.moviedb.MainApplication
import com.quanda.moviedb.data.constants.ApiParam
import com.quanda.moviedb.data.model.Tv
import com.quanda.moviedb.data.repository.impl.MovieRepository
import com.quanda.moviedb.ui.base.viewmodel.BaseDataLoadMoreRefreshViewModel
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import javax.inject.Inject

class TvListViewModel(application: Application,
        val tvListNavigator: TvListNavigator) : BaseDataLoadMoreRefreshViewModel<Tv>(
        application) {

    class CustomFactory(val application: Application,
            val tvListNavigator: TvListNavigator) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TvListViewModel(application, tvListNavigator) as T
        }
    }

    @Inject
    lateinit var movieRepository: MovieRepository

    init {
        MainApplication.appComponent.inject(this)
    }

    override fun loadData(page: Int) {
        val hashMap = HashMap<String, String>()
        hashMap.put(ApiParam.PAGE, page.toString())

        launch() {
            try {
                val response = movieRepository.getTvList().await()
                withContext(UI) {
                    currentPage = page
                    if (currentPage == 1) listItem.clear()
                    if (isRefreshing.value == true) resetLoadMore()
                    listItem.addAll(response.results)
                    onLoadSuccess(response)
                }
            } catch (e: Exception) {
                withContext(UI) {
                    onLoadFail(e)
                }
            }
        }
    }

}