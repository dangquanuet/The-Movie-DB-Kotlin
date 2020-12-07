package com.example.moviedb.ui.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.R
import kotlinx.android.synthetic.main.fragment_loadmore_refresh.*

/**
 * should use paging 3
 */
abstract class BaseLoadMoreRefreshFragment<ViewBinding : ViewDataBinding, ViewModel : BaseLoadMoreRefreshViewModel<Item>, Item : Any> :
    BaseFragment<ViewBinding, ViewModel>() {

    override val layoutId: Int = R.layout.fragment_loadmore_refresh

    abstract val listAdapter: BaseListAdapter<Item, out ViewDataBinding>

    open fun getLayoutManager(): RecyclerView.LayoutManager =
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoadMoreRefresh()
    }

    /**
     * setup default loadMore refresh
     */
    open fun setupLoadMoreRefresh() {
        refresh_layout?.setOnRefreshListener {
            viewModel.doRefresh()
        }
        recycler_view?.layoutManager = getLayoutManager()
        recycler_view?.adapter = listAdapter
        viewModel.apply {
            itemList.observe(viewLifecycleOwner, {
                listAdapter.submitList(it)
            })
            firstLoad()
        }
    }

    override fun handleLoading(isLoading: Boolean) {
        // use progress bar
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recycler_view?.adapter = null
    }
}
