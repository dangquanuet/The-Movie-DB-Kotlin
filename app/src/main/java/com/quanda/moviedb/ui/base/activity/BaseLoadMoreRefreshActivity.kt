package com.quanda.moviedb.ui.base.activity

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.quanda.moviedb.R
import com.quanda.moviedb.ui.base.viewmodel.BaseLoadMoreRefreshViewModel
import com.quanda.moviedb.ui.widgets.OnListChangedListener

abstract class BaseLoadMoreRefreshActivity<View : ViewDataBinding, ViewModel : BaseLoadMoreRefreshViewModel<Item>, Item> : BaseActivity<View, ViewModel>() {

    override val layoutId: Int
        get() = R.layout.activity_loadmore_refresh

    lateinit var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = initAdapter()
        layoutManager = initLayoutManager()
        viewModel.listItem.addOnListChangedCallback(
                OnListChangedListener<Item>(adapter))
    }

    abstract fun initAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>

    open fun initLayoutManager(): RecyclerView.LayoutManager = LinearLayoutManager(this)

    override fun handleLoadingChanged(isLoading: Boolean) {
        // implement if need
    }
}