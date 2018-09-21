package com.quanda.moviedb.ui.widgets

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quanda.moviedb.data.constants.Constants

abstract class EndlessRecyclerOnScrollListener(threshold: Int) : RecyclerView.OnScrollListener() {

    // The total number of items in the dataset after the last load
    private var mPreviousTotal: Int = 0
    private var isLoading = true
    private var mFirstVisibleItem: Int = 0
    private var mVisibleItemCount: Int = 0
    private var mTotalItemCount: Int = 0
    private var mNumberThreshold: Int = -1

    init {
        if (threshold >= 1) {
            mNumberThreshold = threshold
        } else {
            mNumberThreshold = Constants.DEFAULT_NUM_VISIBLE_THRESHOLD
        }
    }

    override fun onScrolled(recycler: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recycler, dx, dy)
        mVisibleItemCount = recycler.childCount
        mTotalItemCount = recycler.layoutManager?.itemCount ?: 0
        if (recycler.layoutManager is LinearLayoutManager) {
            mFirstVisibleItem = (recycler.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        } else if (recycler.layoutManager is GridLayoutManager) {
            mFirstVisibleItem = (recycler.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
        } else {
            throw RuntimeException("Un support this kind of LayoutManager ")
        }

        if (isLoading) {
            stateLoading()
        }

        if (!isLoading && mTotalItemCount -
                mVisibleItemCount <= mFirstVisibleItem + mNumberThreshold) {
            // End has been reached
            onLoadMore()
            isLoading = true
        }
    }

    private fun stateLoading() {
        if (mTotalItemCount > mPreviousTotal) {
            isLoading = false
            mPreviousTotal = mTotalItemCount
        }
    }

    fun resetOnLoadMore() {
        mFirstVisibleItem = 0
        mVisibleItemCount = 0
        mTotalItemCount = 0
        mPreviousTotal = 0
        isLoading = true
    }

    abstract fun onLoadMore()
}