package com.example.moviedb.ui.screen.paged

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.FragmentPagedRefreshBinding
import com.example.moviedb.ui.base.BasePagedRefreshFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_paged_refresh.*

@AndroidEntryPoint
class PagedMovieFragment :
    BasePagedRefreshFragment<FragmentPagedRefreshBinding, PagedMovieViewModel, Movie>() {

    override val viewModel: PagedMovieViewModel by viewModels()

    override val pagedListAdapter by lazy {
        PagedMovieAdapter(
            itemClickListener = { toMovieDetail(it) }
        )
    }

    override fun getLayoutManager() = GridLayoutManager(context, 2)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        container?.setBackgroundColor(Color.BLACK)
    }

    private fun toMovieDetail(movie: Movie) {
        findNavController().navigate(PagedMovieFragmentDirections.toGraphMovieDetail(movie))
    }
}