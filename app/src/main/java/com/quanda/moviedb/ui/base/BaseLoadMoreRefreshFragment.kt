package com.quanda.moviedb.ui.base

import android.databinding.ViewDataBinding
import com.quanda.moviedb.BR
import com.quanda.moviedb.R

abstract class BaseLoadMoreRefreshFragment<View : ViewDataBinding, ViewModel : BaseLoadMoreRefreshViewModel<Item>, Item> : BaseFragment<View, ViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.fragment_loadmore_refresh

    override fun handleShowLoading(isLoading: Boolean) {
        // use progress bar
    }
}