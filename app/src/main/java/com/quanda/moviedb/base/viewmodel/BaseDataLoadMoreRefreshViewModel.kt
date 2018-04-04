package com.quanda.moviedb.base.viewmodel

import android.content.Context
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.support.v4.widget.SwipeRefreshLayout
import com.quanda.moviedb.base.navigator.BaseNavigator
import com.quanda.moviedb.constants.Constants
import com.quanda.moviedb.data.source.remote.response.BaseListResponse
import com.quanda.moviedb.widgets.EndlessRecyclerOnScrollListener

abstract class BaseDataLoadMoreRefreshViewModel<Item>(context: Context,
        baseNavigator: BaseNavigator) : BaseDataLoadViewModel(context, baseNavigator) {

    val isRefreshing = ObservableBoolean()
    val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        if (isDataLoading.get() || isRefreshing.get()) return@OnRefreshListener
        isRefreshing.set(true)
        refreshData()
    }

    val isDataLoadMore = ObservableBoolean()
    var currentPage: Int = 0
    var isLastPage: Boolean = false
    val onScrollListener = object : EndlessRecyclerOnScrollListener(
            getLoadMoreThreshold()) {
        override fun onLoadMore() {
            if (isDataLoading.get() || isRefreshing.get() || isDataLoadMore.get() || isLastPage) return
            isDataLoadMore.set(true)
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
        listItem.clear()
        onScrollListener.resetOnLoadMore()
        isLastPage = false
    }

    fun onLoadSuccess(baseListResponse: BaseListResponse<Item>) {
        isLastPage = baseListResponse.page == baseListResponse.totalPages
        isDataLoading.set(false)
        isRefreshing.set(false)
        isDataLoadMore.set(false)
    }

    override fun onLoadFail() {
        super.onLoadFail()
        isRefreshing.set(false)
        isDataLoadMore.set(false)
    }
}