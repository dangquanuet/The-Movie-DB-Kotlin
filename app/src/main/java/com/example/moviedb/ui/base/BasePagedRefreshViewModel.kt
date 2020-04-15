package com.example.moviedb.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.moviedb.data.constants.Constants
import kotlinx.coroutines.launch

abstract class BasePagedRefreshViewModel<Item> : BaseViewModel() {

    // first page number of item list
    open val firstPage = Constants.DEFAULT_FIRST_PAGE

    // number visible threshold
    open val prefetchDistance = Constants.DEFAULT_NUM_VISIBLE_THRESHOLD

    // number item per page
    open val pageSize = Constants.DEFAULT_ITEM_PER_PAGE

    // refresh flag
    val isRefresh = MutableLiveData(false)

    // load more flag
    val isLoadMore = MutableLiveData(false)

    // enable refresh when not loading or not load more
    val enableRefresh = MediatorLiveData<Boolean>().apply {
        value = true
        addSource(isLoading) {
            value = !(isLoading.value == true || isLoadMore.value == true)
        }
        addSource(isLoadMore) {
            value = !(isLoading.value == true || isLoadMore.value == true)
        }
    }

    // data source
    private var dataSource: BasePageKeyedDataSource<Item>? = null

    // paged list config
    private val pagedListConfig: PagedList.Config by lazy {
        PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(2 * pageSize)
            .setPageSize(pageSize)
            .setPrefetchDistance(prefetchDistance)
            .build()
    }

    // item list
    val itemList: LiveData<PagedList<Item>> by lazy {
        LivePagedListBuilder(
            object : DataSource.Factory<Int, Item>() {
                override fun create(): DataSource<Int, Item> {
                    return createDataSource()
                }
            }, pagedListConfig
        ).build()
    }

    // empty list flag
    val isEmptyList = MutableLiveData(false)

    /**
     * refresh data
     */
    fun doRefresh() {
        isRefresh.value = true
        dataSource?.invalidate()
    }

    /**
     * create page keyed data source
     */
    open fun createDataSource(): BasePageKeyedDataSource<Item> {
        return object : BasePageKeyedDataSource<Item>(viewModel = this) {
            override suspend fun loadDataSource(
                loadInitialParams: LoadInitialParams<Int>?,
                loadParams: LoadParams<Int>?
            ): List<Item> {
                return loadData(loadInitialParams, loadParams)
            }
        }.apply {
            dataSource = this
        }
    }

    /**
     * load and return item list from server
     */
    abstract suspend fun loadData(
        loadInitialParams: PageKeyedDataSource.LoadInitialParams<Int>?,
        loadParams: PageKeyedDataSource.LoadParams<Int>?
    ): List<Item>

    /**
     * handler error
     */
    override suspend fun onError(throwable: Throwable) {
        super.onError(throwable)
        // reset load
        hideLoadMoreRefresh()
    }

    /**
     * hide loading, load more and refresh indicator
     */
    fun hideLoadMoreRefresh() {
        hideLoading()
        isRefresh.value = false
        isLoadMore.value = false
    }
}

/**
 * page keyed data source uses page number to request data
 */
abstract class BasePageKeyedDataSource<Item>(
    private val viewModel: BasePagedRefreshViewModel<Item>
) : PageKeyedDataSource<Int, Item>() {

    /**
     * load first page
     */
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Item>
    ) {
        viewModel.viewModelScope.launch {
            try {
                viewModel.showLoading()
                val itemList = loadDataSource(loadInitialParams = params)
                viewModel.isEmptyList.value = itemList.isEmpty()
                callback.onResult(
                    itemList,
                    null,
                    // last page
                    if (itemList.size < viewModel.pageSize) null
                    // load next page
                    else viewModel.firstPage + 1
                )
            } catch (e: Throwable) {
                viewModel.onError(e)
            } finally {
                viewModel.hideLoadMoreRefresh()
            }
        }
    }

    /**
     * load previous page
     */
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        viewModel.viewModelScope.launch {
            try {
                val itemList = loadDataSource(loadParams = params)
                callback.onResult(
                    itemList,
                    // is first page
                    if (params.key == viewModel.firstPage) null
                    else (params.key - 1)
                )
            } catch (e: Throwable) {
                viewModel.onError(e)
            } finally {
                viewModel.hideLoadMoreRefresh()
            }
        }
    }

    /**
     * load next page
     */
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        // disable load more when loading or refresh
        if (viewModel.isLoading.value == true || viewModel.isRefresh.value == true) return

        viewModel.viewModelScope.launch {
            try {
                viewModel.isLoadMore.value = true
                val itemList = loadDataSource(loadParams = params)
                callback.onResult(
                    itemList,
                    // last page
                    if (itemList.size < viewModel.pageSize) null
                    // load next page
                    else (params.key + 1)
                )
            } catch (e: Throwable) {
                viewModel.onError(e)
            } finally {
                viewModel.hideLoadMoreRefresh()
            }
        }
    }

    /**
     * load data
     */
    abstract suspend fun loadDataSource(
        loadInitialParams: LoadInitialParams<Int>? = null,
        loadParams: LoadParams<Int>? = null
    ): List<Item>
}