package com.quanda.moviedb.ui.base

import androidx.databinding.ViewDataBinding
import com.quanda.moviedb.R

abstract class BaseLoadMoreRefreshFragment<View : ViewDataBinding, ViewModel : BaseLoadMoreRefreshViewModel<Item>, Item> : BaseFragment<View, ViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_loadmore_refresh

    override fun handleShowLoading(isLoading: Boolean) {
        // use progress bar
    }
}