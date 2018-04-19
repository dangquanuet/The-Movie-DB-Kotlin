package com.quanda.moviedb.widgets

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ObservableBoolean
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.quanda.moviedb.R
import com.quanda.moviedb.databinding.LayoutPtrRecyclerViewBinding

class PullRefreshRecyclerView(context: Context, attributeSet: AttributeSet) : FrameLayout(context,
        attributeSet) {

    val layoutManager = MutableLiveData<RecyclerView.LayoutManager>()
    val adapter = MutableLiveData<RecyclerView.Adapter<RecyclerView.ViewHolder>>()
    val onScrollListener = MutableLiveData<RecyclerView.OnScrollListener>()
    // can not get lifecycle from custom view, so use ObserveField
    val isRefreshing = ObservableBoolean()
    val onRefreshListener = MutableLiveData<SwipeRefreshLayout.OnRefreshListener>()
    val binding: LayoutPtrRecyclerViewBinding

    init {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.layout_ptr_recycler_view, this, true)
        binding.view = this
    }

    fun setRefreshing(isRefreshing: Boolean) {
        this.isRefreshing.set(isRefreshing)
    }

    fun scrollToPosition(position: Int) {
        binding.ptrRecyclerView.scrollToPosition(position)
    }

    fun smoothScrollToPosition(position: Int) {
        binding.ptrRecyclerView.smoothScrollToPosition(position)
    }

    fun turnOnRefresh() {
        binding.swipeLayout.isEnabled = true
    }

    fun turnOffRefresh() {
        binding.swipeLayout.isEnabled = false
    }
}