package com.quanda.moviedb.base.fragment

import android.databinding.ViewDataBinding
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.quanda.moviedb.R
import com.quanda.moviedb.base.viewmodel.BaseDataLoadMoreRefreshViewModel
import com.quanda.moviedb.widgets.OnListChangedListener

abstract class BaseDataLoadMoreRefreshFragment<View : ViewDataBinding, ViewModel : BaseDataLoadMoreRefreshViewModel<Item>, Item> : BaseDataLoadFragment<View, ViewModel>() {

    lateinit var mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>

    override fun getLayoutId(): Int {
        return R.layout.fragment_base_loadmore_refresh
    }

    override fun initData() {
        super.initData()
        mAdapter = getAdapter()
        viewModel.listItem.addOnListChangedCallback(
                OnListChangedListener<Item>(mAdapter))
    }

    abstract fun getAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>

    open fun getLayoutManager(): RecyclerView.LayoutManager = LinearLayoutManager(context)

    override fun handleLoadingChanged(isLoading: Boolean) {
        // implement if need
    }
}