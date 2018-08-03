package com.quanda.moviedb.ui.screen.tv

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.quanda.moviedb.data.model.Tv
import com.quanda.moviedb.databinding.FragmentLoadmoreRefreshBinding
import com.quanda.moviedb.ui.base.fragment.BaseLoadMoreRefreshFragment
import com.quanda.moviedb.ui.screen.tv2.TvListAdapter2

class TvListFragment : BaseLoadMoreRefreshFragment<FragmentLoadmoreRefreshBinding, TvListViewModel, Tv>(), TvListNavigator {

    companion object {
        const val TAG = "TvListFragment"

        fun newInstance() = TvListFragment()
    }

    override val viewModel: TvListViewModel
        get() = ViewModelProviders.of(this, viewModelFactory)
                .get(TvListViewModel::class.java)
                .apply {
                    navigator = this@TvListFragment
                }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            view = this@TvListFragment
            viewModel = this@TvListFragment.viewModel
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
        return TvListAdapter2() as RecyclerView.Adapter<RecyclerView.ViewHolder>
    }

    override fun initLayoutManager(): RecyclerView.LayoutManager = GridLayoutManager(context, 2)
}