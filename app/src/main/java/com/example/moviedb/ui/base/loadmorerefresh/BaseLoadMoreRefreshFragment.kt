package com.example.moviedb.ui.base.loadmorerefresh

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.moviedb.R
import com.example.moviedb.ui.base.BaseFragment
import com.example.moviedb.ui.base.BaseListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * should use paging 3
 */
abstract class BaseLoadMoreRefreshFragment<ViewBinding : ViewDataBinding, ViewModel : BaseLoadMoreRefreshViewModel<Item>, Item : Any> :
    BaseFragment<ViewBinding, ViewModel>() {

    override val layoutId: Int = R.layout.fragment_loadmore_refresh
    abstract val listAdapter: BaseListAdapter<Item, out ViewDataBinding>
    abstract val swipeRefreshLayout: SwipeRefreshLayout?
    abstract val recyclerView: RecyclerView?

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
        swipeRefreshLayout?.setOnRefreshListener {
            viewModel.doRefresh()
        }
        recyclerView?.layoutManager = getLayoutManager()
        recyclerView?.adapter = listAdapter
        recyclerView?.setHasFixedSize(true)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.itemList.collectLatest {
                    listAdapter.submitList(it)
                }
            }
        }
        viewModel.firstLoad()
    }

    override fun handleLoading(isLoading: Boolean) {
        // use progress bar
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView?.adapter = null
    }
}
