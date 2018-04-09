package com.quanda.moviedb.ui.movie

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.quanda.moviedb.base.BaseViewHolderBinding
import com.quanda.moviedb.base.activity.BaseDataLoadMoreRefreshActivity
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.databinding.ActivityBaseLoadmoreRefreshBinding

class MovieListActivity : BaseDataLoadMoreRefreshActivity<ActivityBaseLoadmoreRefreshBinding, MovieListViewModel, Movie>(), MovieListNavigator {

    override fun initViewModel(): MovieListViewModel {
        return MovieListViewModel(this, this)
    }

    override fun initData() {
        super.initData()
        binding.view = this
        binding.viewModel = viewModel
        binding.recyclerView.adapter.set(mAdapter)

        viewModel.isDataLoading.set(true)
        viewModel.loadData(1)
    }

    override fun getAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return MovieListAdapter(this, viewModel.listItem,
                object : BaseViewHolderBinding.OnItemCLickListener<Movie> {
                    override fun onItemClick(position: Int, data: Movie) {
                        // TODO
                    }
                }) as RecyclerView.Adapter<RecyclerView.ViewHolder>
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager = GridLayoutManager(this, 2)
}