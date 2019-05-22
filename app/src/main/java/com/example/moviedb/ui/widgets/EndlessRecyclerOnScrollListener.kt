package com.example.moviedb.ui.widgets

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.data.constants.Constants
import com.example.moviedb.utils.safeLog

abstract class EndlessRecyclerOnScrollListener(
    threshold: Int = Constants.DEFAULT_NUM_VISIBLE_THRESHOLD
) : RecyclerView.OnScrollListener() {

    // The total number of items in the dataset after the last load
    private var previousTotal: Int = 0
    private var isLoading = true
    private var firstVisibleItem: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var numberThreshold: Int = if (threshold >= 1) {
        threshold
    } else {
        Constants.DEFAULT_NUM_VISIBLE_THRESHOLD
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        visibleItemCount = recyclerView.childCount
        totalItemCount = recyclerView.layoutManager?.itemCount ?: 0
        when (val layoutManager = recyclerView.layoutManager) {
            is LinearLayoutManager -> {
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
            }
            is GridLayoutManager -> {
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
            }
            else -> {
                Exception("Unsupported LayoutManage").safeLog()
            }
        }

        if (isLoading) {
            stateLoading()
        }

        if (isLoading.not()
            && totalItemCount - visibleItemCount <= firstVisibleItem + numberThreshold
        ) {
            onLoadMore()
            isLoading = true
        }
    }

    // check load more is success
    private fun stateLoading() {
        if (totalItemCount > previousTotal) {
            isLoading = false
            previousTotal = totalItemCount
        }
    }

    // TODO check logic load more when load fail

    fun resetOnLoadMore() {
        firstVisibleItem = 0
        visibleItemCount = 0
        totalItemCount = 0
        previousTotal = 0
        isLoading = true
    }

    abstract fun onLoadMore()
}