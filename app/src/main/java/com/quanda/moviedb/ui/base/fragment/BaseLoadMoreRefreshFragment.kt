package com.quanda.moviedb.ui.base.fragment

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.quanda.moviedb.R
import com.quanda.moviedb.ui.base.viewmodel.BaseLoadMoreRefreshViewModel
import com.quanda.moviedb.ui.widgets.OnListChangedListener

abstract class BaseLoadMoreRefreshFragment<View : ViewDataBinding, ViewModel : BaseLoadMoreRefreshViewModel<Item>, Item> : BaseFragment<View, ViewModel>() {

    lateinit var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>

    lateinit var layoutManager: RecyclerView.LayoutManager

    override val layoutId: Int
        get() = R.layout.fragment_loadmore_refresh

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = initAdapter()
        layoutManager = initLayoutManager()
        viewModel.listItem.addOnListChangedCallback(
                OnListChangedListener<Item>(adapter))
    }

    abstract fun initAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>

    open fun initLayoutManager(): RecyclerView.LayoutManager = LinearLayoutManager(context)

    override fun handleLoadingChanged(isLoading: Boolean) {
        // implement if need
    }
}