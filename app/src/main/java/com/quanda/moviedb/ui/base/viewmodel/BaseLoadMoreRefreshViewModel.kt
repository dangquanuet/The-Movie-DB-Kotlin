package com.quanda.moviedb.ui.base.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.support.v4.widget.SwipeRefreshLayout
import com.quanda.moviedb.data.constants.Constants
import com.quanda.moviedb.ui.widgets.EndlessRecyclerOnScrollListener

abstract class BaseLoadMoreRefreshViewModel<Item>() : BaseViewModel() {

    val isRefreshing = MutableLiveData<Boolean>().apply { value = false }
    val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        if (isLoading.value == true || isRefreshing.value == true) return@OnRefreshListener
        isRefreshing.value = true
        refreshData()
    }

    val isLoadMore = MutableLiveData<Boolean>().apply { value = false }
    var currentPage = MutableLiveData<Int>().apply { value = 0 }
    var isLastPage = MutableLiveData<Boolean>().apply { value = false }

    val onScrollListener = object : EndlessRecyclerOnScrollListener(getLoadMoreThreshold()) {
        override fun onLoadMore() {
            if (isLoading.value == true
                    || isRefreshing.value == true
                    || isLoadMore.value == true
                    || isLastPage.value == true) return
            isLoadMore.value = true
            loadMore()
        }
    }
    val listItem = MutableLiveData<ArrayList<Item>>()

    abstract fun loadData(page: Int)

    fun isFirst() = currentPage.value == 0
            && (listItem.value == null || listItem.value?.size == 0)

    fun firstLoad() {
        isLoading.value = true
        loadData(1)
    }

    fun refreshData() {
        loadData(1)
    }

    fun loadMore() {
        loadData(currentPage.value?.plus(1) ?: 1)
    }

    fun getLoadMoreThreshold() = Constants.DEFAULT_NUM_VISIBLE_THRESHOLD

    fun getNumberItemPerPage() = Constants.DEFAULT_ITEM_PER_PAGE

    fun resetLoadMore() {
        onScrollListener.resetOnLoadMore()
        isLastPage.value = false
    }

    fun onLoadSuccess(listSize: Int) {
        isLastPage.value = listSize < getNumberItemPerPage()
        isLoading.value = false
        isRefreshing.value = false
        isLoadMore.value = false
    }

    override fun onLoadFail(e: Throwable) {
        super.onLoadFail(e)
        isRefreshing.value = false
        isLoadMore.value = false
    }
}