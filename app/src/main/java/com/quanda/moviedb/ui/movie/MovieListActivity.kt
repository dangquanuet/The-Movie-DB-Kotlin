package com.quanda.moviedb.ui.movie

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.quanda.moviedb.base.BaseViewHolderBinding
import com.quanda.moviedb.base.activity.BaseDataLoadMoreRefreshActivity
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.databinding.ActivityBaseLoadmoreRefreshBinding

class MovieListActivity : BaseDataLoadMoreRefreshActivity<ActivityBaseLoadmoreRefreshBinding, MovieListViewModel, Movie>(), MovieListNavigator {

    override fun initViewModel(): MovieListViewModel {
        return ViewModelProviders.of(this, MovieListViewModel.CustomFactory(application, this)).get(
                MovieListViewModel::class.java)
    }

    override fun initData() {
        super.initData()
        binding.apply {
            view = this@MovieListActivity
            viewModel = this@MovieListActivity.viewModel
            recyclerView.adapter.value = adapter
        }

        viewModel.apply {
            isDataLoading.value = true
            loadData(1)
        }
    }

    override fun initAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return MovieListAdapter(this, viewModel.listItem,
                object : BaseViewHolderBinding.OnItemCLickListener<Movie> {
                    override fun onItemClick(position: Int, data: Movie) {
                        // TODO
                    }
                }) as RecyclerView.Adapter<RecyclerView.ViewHolder>
    }

    override fun initLayoutManager(): RecyclerView.LayoutManager = GridLayoutManager(this, 2)
}