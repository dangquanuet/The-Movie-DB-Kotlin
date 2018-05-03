package com.quanda.moviedb.ui.base.fragment

import android.databinding.ViewDataBinding
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.quanda.moviedb.R
import com.quanda.moviedb.ui.base.viewmodel.BaseDataLoadMoreRefreshViewModel
import com.quanda.moviedb.ui.widgets.OnListChangedListener

abstract class BaseDataLoadMoreRefreshFragment<View : ViewDataBinding, ViewModel : BaseDataLoadMoreRefreshViewModel<Item>, Item> : BaseDataLoadFragment<View, ViewModel>() {

    lateinit var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    lateinit var layoutManager: RecyclerView.LayoutManager

    override fun getLayoutId(): Int = R.layout.fragment_base_loadmore_refresh

    override fun initData() {
        super.initData()
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