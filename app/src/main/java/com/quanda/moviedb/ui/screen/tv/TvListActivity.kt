package com.quanda.moviedb.ui.screen.tv

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.quanda.moviedb.data.model.Tv
import com.quanda.moviedb.databinding.ActivityBaseLoadmoreRefreshBinding
import com.quanda.moviedb.ui.base.BaseViewHolderBinding
import com.quanda.moviedb.ui.base.activity.BaseLoadMoreRefreshActivity
import com.quanda.moviedb.ui.screen.tv2.TvListAdapter2

class TvListActivity : BaseLoadMoreRefreshActivity<ActivityBaseLoadmoreRefreshBinding, TvListViewModel, Tv>(), TvListNavigator {

    override val viewModel: TvListViewModel
        get() = ViewModelProviders.of(this, viewModelFactory).get(TvListViewModel::class.java)
                .apply {
                    navigator = this@TvListActivity
                }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

//    override fun initAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder> {
//        return TvListAdapter(this, viewModel.listItem,
//                object : BaseViewHolderBinding.OnItemCLickListener<Tv> {
//                    override fun onItemClick(position: Int, data: Tv) {
//                        // TODO
//                    }
//                }) as RecyclerView.Adapter<RecyclerView.ViewHolder>
//    }

    override fun initAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return TvListAdapter2(viewModel.listItem,
                object : BaseViewHolderBinding.OnItemCLickListener<Tv> {
                    override fun onItemClick(position: Int, data: Tv) {
                        // TODO
                    }
                }, TvListAdapter2.Callback()) as RecyclerView.Adapter<RecyclerView.ViewHolder>
    }

    override fun initLayoutManager(): RecyclerView.LayoutManager = GridLayoutManager(this, 2)
}