package com.example.moviedb.ui.base.items

import android.os.Bundle
import android.view.View
import androidx.core.widget.ContentLoadingProgressBar
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
import timber.log.Timber

abstract class ItemsFragment<ViewBinding : ViewDataBinding, ViewModel : ItemsViewModel<Item>, Item : Any> :
    BaseFragment<ViewBinding, ViewModel>() {

    override val layoutId: Int = R.layout.fragment_items
    abstract val listAdapter: BaseListAdapter<Item, out ViewDataBinding>
    abstract val swipeRefreshLayout: SwipeRefreshLayout
    abstract val progressLoading: ContentLoadingProgressBar
    abstract val recyclerView: RecyclerView

    open fun getLayoutManager(): RecyclerView.LayoutManager =
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    open fun setupUi() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.doRefresh()
        }
        recyclerView.layoutManager = getLayoutManager()
        recyclerView.adapter = listAdapter
        recyclerView.setHasFixedSize(true)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.itemsUiState.collectLatest { uiState ->
                    Timber.d("uiState collectLatest $uiState")
                    swipeRefreshLayout.isRefreshing = uiState.isRefreshing
                    handleLoading(isLoading = uiState.isLoading)
                    listAdapter.submitList(uiState.items)
                    handleError(errorType = uiState.errorType)
                }
            }
        }
        viewModel.firstLoad()
    }

    override fun handleLoading(isLoading: Boolean) {
        progressLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView.adapter = null
    }
}
