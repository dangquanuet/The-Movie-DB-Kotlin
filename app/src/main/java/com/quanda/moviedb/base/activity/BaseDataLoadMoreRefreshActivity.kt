package com.quanda.moviedb.base.activity

import android.databinding.ObservableList
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import com.quanda.moviedb.R
import com.quanda.moviedb.base.viewmodel.BaseDataLoadMoreRefreshViewModel
import com.quanda.moviedb.widgets.OnListChangedListener

abstract class BaseDataLoadMoreRefreshActivity<View : ViewDataBinding, ViewModel : BaseDataLoadMoreRefreshViewModel<Item>, Item> : BaseDataLoadActivity<View, ViewModel>() {

    lateinit var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>

    override fun getLayoutId(): Int {
        return R.layout.activity_base_loadmore_refresh
    }

    override fun initData() {
        super.initData()
        viewModel.listItem.addOnListChangedCallback(
                OnListChangedListener<ObservableList<Item>, Item>(adapter))
        initRecyclerView()
    }

    abstract fun initRecyclerView()

    override fun handleLoadingChanged(isLoading: Boolean) {
        // implement if need
    }
}