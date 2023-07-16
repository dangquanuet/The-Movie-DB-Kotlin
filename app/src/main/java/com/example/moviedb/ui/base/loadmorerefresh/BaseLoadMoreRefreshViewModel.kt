package com.example.moviedb.ui.base.loadmorerefresh

import com.example.moviedb.data.constant.Constants
import com.example.moviedb.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * should use paging 3
 */
abstract class BaseLoadMoreRefreshViewModel<Item>() : BaseViewModel() {

    // refresh flag
    val isRefreshing = MutableStateFlow(false)

    // load more flag
    private val isLoadMore = MutableStateFlow(false)
    private var loadMoreTimeMillis = 0L

    // current page
    private val currentPage = MutableStateFlow(getPreFirstPage())

    // last page flag
    private val isLastPage = MutableStateFlow(false)

    // item list
    val itemList = MutableStateFlow(arrayListOf<Item>())

    // empty list flag
    val isEmptyList = MutableStateFlow(false)

    /**
     * load data
     */
    abstract fun loadData(page: Int)

    /**
     * check first time load data
     */
    private fun isFirst() = currentPage.value == getPreFirstPage()
            && itemList.value.isEmpty()

    /**
     * first load
     */
    fun firstLoad() {
        if (isFirst()) {
            showLoading()
            loadData(getFirstPage())
        }
    }

    fun doRefresh() {
        when {
            isLoading() || isRefreshing.value -> {}

            else -> {
                isRefreshing.value = true
                refreshData()
            }
        }
    }

    /**
     * load first page
     */
    private fun refreshData() {
        loadData(getFirstPage())
    }

    fun onBind(position: Int) {
//        Timber.v("Check load more on $position")
        if (itemList.value.size - position < getLoadMoreThreshold()) {
            doLoadMore()
        }
    }

    fun doLoadMore() {
        when {
            isLoading()
                    || isRefreshing.value
                    || isLoadMore.value
                    || isLastPage.value
                    || System.currentTimeMillis() - loadMoreTimeMillis < 2000 -> {
            }

            else -> {
                isLoadMore.value = true
                loadMoreTimeMillis = System.currentTimeMillis()
                loadMore()
            }
        }
    }

    /**
     * load next page
     */
    private fun loadMore() {
        loadData(currentPage.value.plus(1))
    }

    /**
     * override if first page is not 1
     */
    open fun getFirstPage() = Constants.DEFAULT_FIRST_PAGE

    private fun getPreFirstPage() = getFirstPage() - 1

    /**
     * override if need change number visible threshold
     */
    protected open fun getLoadMoreThreshold() = Constants.DEFAULT_NUM_VISIBLE_THRESHOLD

    /**
     * override if need change number item per page
     */
    protected open fun getNumberItemPerPage() = Constants.DEFAULT_ITEM_PER_PAGE

    /**
     * reset load more
     */
    private fun resetLoadMore() {
        isLastPage.value = false
    }

    /**
     * handle load success
     */
    fun onLoadSuccess(page: Int, items: List<Item>?) {
        // load success then update current page
        currentPage.value = page
        // case load first page then clear data from listItem
        if (currentPage.value == getFirstPage()) itemList.value.clear()
        // case refresh then reset load more
        if (isRefreshing.value) resetLoadMore()

        // add new data to listItem
        if (items?.isNotEmpty() == true) {
            itemList.value = arrayListOf<Item>().apply {
                addAll(itemList.value)
                addAll(items)
            }
        }

        // check last page
        isLastPage.value = (items?.size ?: 0) < getNumberItemPerPage()

        // reset load
        isRefreshing.value = false
        isLoadMore.value = false
        showSuccess()
        // check empty list
        checkEmptyList()
    }

    /**
     * handle load fail
     */
    override suspend fun onError(throwable: Throwable) {
        super.onError(throwable)
        // reset load
        isRefreshing.value = false
        isLoadMore.value = false
        // check empty list
        checkEmptyList()
    }

    /**
     * check list is empty
     */
    private fun checkEmptyList() {
        isEmptyList.value = itemList.value.isEmpty()
    }
}
