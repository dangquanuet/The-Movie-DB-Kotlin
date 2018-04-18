package com.quanda.moviedb.base.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableArrayList
import android.support.v4.widget.SwipeRefreshLayout
import com.quanda.moviedb.constants.Constants
import com.quanda.moviedb.data.source.remote.response.BaseListResponse
import com.quanda.moviedb.widgets.EndlessRecyclerOnScrollListener

abstract class BaseDataLoadMoreRefreshViewModel<Item>(
        application: Application) : BaseDataLoadViewModel(
        application) {

    val isRefreshing = MutableLiveData<Boolean>().apply { value = false }
    val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        if (isDataLoading.value == true || isRefreshing.value == true) return@OnRefreshListener
        isRefreshing.value = true
        refreshData()
    }

    val isDataLoadMore = MutableLiveData<Boolean>().apply { value = false }
    var currentPage: Int = 0
    var isLastPage: Boolean = false
    val onScrollListener = object : EndlessRecyclerOnScrollListener(
            getLoadMoreThreshold()) {
        override fun onLoadMore() {
            if (isDataLoading.value == true || isRefreshing.value == true || isDataLoadMore.value == true || isLastPage) return
            isDataLoadMore.value = true
            loadMore()
        }
    }
    val listItem = ObservableArrayList<Item>()

    abstract fun loadData(page: Int)

    fun refreshData() {
        loadData(1)
    }

    fun loadMore() {
        loadData(currentPage + 1)
    }

    fun getLoadMoreThreshold() = Constants.DEFAULT_NUM_VISIBLE_THRESHOLD

    fun getNumberItemPerPage() = Constants.DEFAULT_ITEM_PER_PAGE

    fun resetLoadMore() {
        onScrollListener.resetOnLoadMore()
        isLastPage = false
    }

    fun onLoadSuccess(baseListResponse: BaseListResponse<Item>) {
        isLastPage = baseListResponse.page == baseListResponse.totalPages
        isDataLoading.value = false
        isRefreshing.value = false
        isDataLoadMore.value = false
    }

    override fun onLoadFail(e: Throwable) {
        super.onLoadFail(e)
        isRefreshing.value = false
        isDataLoadMore.value = false
    }
}