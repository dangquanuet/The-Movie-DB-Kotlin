package com.example.moviedb.ui.base.items

import com.example.moviedb.data.constant.Constants
import com.example.moviedb.ui.base.BaseViewModel
import com.example.moviedb.ui.base.ErrorType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class ItemsUiState<Item>(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLoadMore: Boolean = false,
    val items: List<Item> = emptyList(),
    val errorType: ErrorType? = null
)

abstract class ItemsViewModel<Item> : BaseViewModel() {
    private val _itemsUiState = MutableStateFlow(ItemsUiState<Item>())
    val itemsUiState: StateFlow<ItemsUiState<Item>> = _itemsUiState
    private var loadMoreTimeMillis = 0L

    // override if first page is not 1
    protected open val firstPage = Constants.DEFAULT_FIRST_PAGE
    private val preFirstPage = firstPage - 1

    // current page
    private var currentPage: Int = preFirstPage

    // last page flag
    private var isLastPage: Boolean = false

    // empty list flag
    private val isEmptyList = MutableStateFlow(false)

    // override if need change number visible threshold
    protected open val loadMoreThreshold = Constants.DEFAULT_NUM_VISIBLE_THRESHOLD

    // override if need change number item per page
    protected open val numberItemPerPage = Constants.DEFAULT_ITEM_PER_PAGE

    /**
     * load data
     */
    abstract fun loadData(page: Int)

    /**
     * first load
     */
    fun firstLoad() {
        if (currentPage == preFirstPage
            && _itemsUiState.value.items.isEmpty()
        ) {
            _itemsUiState.update {
                it.copy(isLoading = true)
            }
            loadData(firstPage)
        }
    }

    fun doRefresh() {
        when {
            _itemsUiState.value.isLoading
                    || _itemsUiState.value.isRefreshing -> {
            }

            else -> {
                _itemsUiState.update {
                    it.copy(isRefreshing = true)
                }
                refreshData()
            }
        }
    }

    /**
     * load first page
     */
    private fun refreshData() {
        loadData(firstPage)
    }

    fun onBind(position: Int) {
//        Timber.v("Check load more on $position")
        if (_itemsUiState.value.items.size - position <= loadMoreThreshold) {
            doLoadMore()
        }
    }

    fun doLoadMore() {
        when {
            _itemsUiState.value.isLoading
                    || _itemsUiState.value.isRefreshing
                    || _itemsUiState.value.isLoadMore
                    || isLastPage
                    || System.currentTimeMillis() - loadMoreTimeMillis < 2000 -> {
            }

            else -> {
                _itemsUiState.update {
                    it.copy(isLoadMore = true)
                }
                loadMoreTimeMillis = System.currentTimeMillis()
                loadMore()
            }
        }
    }

    /**
     * load next page
     */
    private fun loadMore() {
        loadData(currentPage + 1)
    }

    /**
     * handle load success
     */
    fun onLoadSuccess(page: Int, items: List<Item>?) {
        currentPage = page
        isLastPage = (items?.size ?: 0) < numberItemPerPage
        _itemsUiState.update {
            it.copy(
                isLoading = false,
                isRefreshing = false,
                isLoadMore = false,
                items = arrayListOf<Item>().apply {
                    if (currentPage != firstPage) {
                        addAll(_itemsUiState.value.items)
                    }
                    if (items?.isNotEmpty() == true) {
                        addAll(items)
                    }
                },
                errorType = null
            )
        }
        checkEmptyList()
    }

    /**
     * handle load fail
     */
    override fun onError(throwable: Throwable) {
        _itemsUiState.update {
            it.copy(
                isLoading = false,
                isRefreshing = false,
                isLoadMore = false,
                errorType = toErrorType(throwable = throwable)
            )
        }
        checkEmptyList()
    }

    override fun hideError() {
        _itemsUiState.update {
            it.copy(errorType = null)
        }
    }

    /**
     * check list is empty
     */
    private fun checkEmptyList() {
        isEmptyList.value = _itemsUiState.value.items.isEmpty()
    }
}