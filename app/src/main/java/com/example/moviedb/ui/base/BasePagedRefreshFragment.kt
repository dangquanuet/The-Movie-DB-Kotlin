package com.example.moviedb.ui.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.moviedb.R

/**
 * deprecated, should use paging 3
 */
abstract class BasePagedRefreshFragment<ViewBinding : ViewDataBinding, ViewModel : BasePagedRefreshViewModel<Item>, Item : Any> :
    BaseFragment<ViewBinding, ViewModel>() {

    override val layoutId: Int = R.layout.fragment_paged_refresh

    abstract val pagedListAdapter: BasePagedListAdapter<Item, out ViewDataBinding>

    abstract val swipeRefreshLayout: SwipeRefreshLayout?

    abstract val recyclerView: RecyclerView?

    open fun getLayoutManager(): RecyclerView.LayoutManager =
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPagedRefresh()
    }

    /**
     * setup default paged refresh
     */
    open fun setupPagedRefresh() {
        swipeRefreshLayout?.setOnRefreshListener {
            viewModel.doRefresh()
        }
        recyclerView?.layoutManager = getLayoutManager()
        recyclerView?.adapter = pagedListAdapter
        recyclerView?.setHasFixedSize(true)
        viewModel.apply {
            itemList.observe(viewLifecycleOwner, {
                pagedListAdapter.submitList(it)
            })
        }
    }

    override fun handleLoading(isLoading: Boolean) {
        // use progress bar
    }
}
