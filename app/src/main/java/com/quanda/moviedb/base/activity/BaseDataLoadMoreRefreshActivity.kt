package com.quanda.moviedb.base.activity

import android.databinding.ViewDataBinding
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.quanda.moviedb.R
import com.quanda.moviedb.base.viewmodel.BaseDataLoadMoreRefreshViewModel
import com.quanda.moviedb.widgets.OnListChangedListener

abstract class BaseDataLoadMoreRefreshActivity<View : ViewDataBinding, ViewModel : BaseDataLoadMoreRefreshViewModel<Item>, Item> : BaseDataLoadActivity<View, ViewModel>() {

    lateinit var mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>

    override fun getLayoutId(): Int {
        return R.layout.activity_base_loadmore_refresh
    }

    override fun initData() {
        super.initData()
        mAdapter = getAdapter()
        viewModel.listItem.addOnListChangedCallback(
                OnListChangedListener<Item>(mAdapter))
    }

    abstract fun getAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>

    open fun getLayoutManager(): RecyclerView.LayoutManager = LinearLayoutManager(this)

    override fun handleLoadingChanged(isLoading: Boolean) {
        // implement if need
    }
}