package com.quanda.moviedb.ui.screen.tv

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.quanda.moviedb.data.model.Tv
import com.quanda.moviedb.databinding.ActivityBaseLoadmoreRefreshBinding
import com.quanda.moviedb.ui.base.BaseViewHolderBinding
import com.quanda.moviedb.ui.base.activity.BaseDataLoadMoreRefreshActivity

class TvListActivity : BaseDataLoadMoreRefreshActivity<ActivityBaseLoadmoreRefreshBinding, TvListViewModel, Tv>(), TvListNavigator {

    override fun initViewModel(): TvListViewModel {
        return ViewModelProviders.of(this).get(TvListViewModel::class.java)
                .apply {
                    navigator = this@TvListActivity
                }
    }

    override fun initData() {
        super.initData()
        binding.apply {
            view = this@TvListActivity
            viewModel = this@TvListActivity.viewModel
            recyclerView.adapter.value = adapter
        }

        viewModel.apply {
            isDataLoading.value = true
            loadData(1)
        }
    }

    override fun initAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return TvListAdapter(this, viewModel.listItem,
                object : BaseViewHolderBinding.OnItemCLickListener<Tv> {
                    override fun onItemClick(position: Int, data: Tv) {
                        // TODO
                    }
                }) as RecyclerView.Adapter<RecyclerView.ViewHolder>
    }

    override fun initLayoutManager(): RecyclerView.LayoutManager = GridLayoutManager(this, 2)
}