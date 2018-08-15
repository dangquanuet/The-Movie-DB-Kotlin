package com.quanda.moviedb.ui.base.fragment

import android.databinding.ViewDataBinding
import com.quanda.moviedb.BR
import com.quanda.moviedb.R
import com.quanda.moviedb.ui.base.viewmodel.BaseLoadMoreRefreshViewModel

abstract class BaseLoadMoreRefreshFragment<View : ViewDataBinding, ViewModel : BaseLoadMoreRefreshViewModel<Item>, Item> : BaseFragment<View, ViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.fragment_loadmore_refresh

    override fun handleShowLoading(isLoading: Boolean) {
        // use progress bar
    }
}