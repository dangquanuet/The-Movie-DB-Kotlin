package com.example.moviedb.ui.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.R
import kotlinx.android.synthetic.main.fragment_paged_refresh.*

/**
 * deprecated, should use paging 3
 */
abstract class BasePagedRefreshFragment<ViewBinding : ViewDataBinding, ViewModel : BasePagedRefreshViewModel<Item>, Item : Any> :
    BaseFragment<ViewBinding, ViewModel>() {

    override val layoutId: Int = R.layout.fragment_paged_refresh

    abstract val pagedListAdapter: BasePagedListAdapter<Item, out ViewDataBinding>

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
        refresh_layout?.setOnRefreshListener {
            viewModel.doRefresh()
        }
        recycler_view?.layoutManager = getLayoutManager()
        recycler_view?.adapter = pagedListAdapter
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
