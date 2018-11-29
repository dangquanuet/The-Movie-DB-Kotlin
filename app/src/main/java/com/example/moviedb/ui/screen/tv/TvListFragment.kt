package com.example.moviedb.ui.screen.tv

import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviedb.data.model.Tv
import com.example.moviedb.databinding.FragmentLoadmoreRefreshBinding
import com.example.moviedb.ui.base.BaseLoadMoreRefreshFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvListFragment :
    BaseLoadMoreRefreshFragment<FragmentLoadmoreRefreshBinding, TvListViewModel, Tv>() {

    companion object {
        const val TAG = "TvListFragment"

        fun newInstance() = TvListFragment()
    }

    override val viewModel by viewModel<TvListViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = TvListAdapter(
            itemClickListener = { goToTvDetail(it) }
        )

        viewBinding.apply {
            root.setBackgroundColor(Color.BLACK)
            recyclerView.apply {
                layoutManager = GridLayoutManager(context, 2)
                this.adapter = adapter
            }
        }

        viewModel.apply {
            listItem.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })
            firstLoad()
        }
    }


    fun goToTvDetail(tv: Tv) {

    }
}