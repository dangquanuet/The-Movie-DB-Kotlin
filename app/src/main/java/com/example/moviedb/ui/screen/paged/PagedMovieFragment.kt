package com.example.moviedb.ui.screen.paged

import android.graphics.Color
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.FragmentPagedRefreshBinding
import com.example.moviedb.ui.base.BasePagedRefreshFragment
import kotlinx.android.synthetic.main.fragment_paged_refresh.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PagedMovieFragment :
    BasePagedRefreshFragment<FragmentPagedRefreshBinding, PagedMovieViewModel, Movie>() {

    override val viewModel: PagedMovieViewModel by viewModel()

    override val pagedListAdapter by lazy {
        PagedMovieAdapter(
            itemClickListener = { toMovieDetail(it) }
        )
    }

    override val layoutManager by lazy {
        GridLayoutManager(context, 2)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        container?.setBackgroundColor(Color.BLACK)
    }

    private fun toMovieDetail(movie: Movie) {
    }
}