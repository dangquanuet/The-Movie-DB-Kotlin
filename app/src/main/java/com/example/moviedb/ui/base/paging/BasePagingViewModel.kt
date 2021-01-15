package com.example.moviedb.ui.base.paging

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.moviedb.data.constants.Constants
import com.example.moviedb.data.source.BasePagingSource
import com.example.moviedb.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class BasePagingViewModel<Item : Any> : BaseViewModel() {

    init {
        showLoading()
    }

    // refresh flag
    val isRefresh by lazy { MutableLiveData(false) }

    // empty list flag
    val isEmptyList by lazy { MutableLiveData(false) }

    // number item per page
    protected open val pageSize by lazy { Constants.DEFAULT_ITEM_PER_PAGE }

    protected open val pagingConfig: PagingConfig by lazy {
        PagingConfig(pageSize = pageSize)
    }

    val items: Flow<PagingData<Item>> by lazy {
        Pager(pagingConfig) {
            createPagingSource().apply {
                pagingSource = this
            }
        }.flow.cachedIn(viewModelScope)
    }

    // paging source
    private var pagingSource: BasePagingSource<Item>? = null

    protected abstract fun createPagingSource(): BasePagingSource<Item>

    /**
     * refresh data
     */
    fun doRefresh() {
        isRefresh.value = true
        pagingSource?.invalidate()
    }

    fun reload() {
        showLoading()
        pagingSource?.invalidate()
    }

    fun handleLoadStates(combinedLoadStates: CombinedLoadStates, itemCount: Int) {
        when {
            combinedLoadStates.refresh is LoadState.Error
                    || combinedLoadStates.append is LoadState.Error
                    || combinedLoadStates.prepend is LoadState.Error -> {
                hideLoadRefresh()
                isEmptyList.value = itemCount == 0
                val error = when {
                    combinedLoadStates.refresh is LoadState.Error -> combinedLoadStates.refresh as LoadState.Error
                    combinedLoadStates.append is LoadState.Error -> combinedLoadStates.append as LoadState.Error
                    combinedLoadStates.prepend is LoadState.Error -> combinedLoadStates.prepend as LoadState.Error
                    else -> null
                }
                if (error != null) {
                    viewModelScope.launch {
                        onError(error.error)
                    }
                }
            }

            combinedLoadStates.refresh is LoadState.NotLoading
                    && combinedLoadStates.append is LoadState.NotLoading
                    && combinedLoadStates.prepend is LoadState.NotLoading -> {
                hideLoadRefresh()
                isEmptyList.value = itemCount == 0
            }
        }
    }

    /**
     * handler error
     */
    override fun onError(throwable: Throwable) {
        super.onError(throwable)
        // reset load
        hideLoadRefresh()
    }

    /**
     * hide loading, load more and refresh indicator
     */
    private fun hideLoadRefresh() {
        hideLoading()
        isRefresh.value = false
    }
}
