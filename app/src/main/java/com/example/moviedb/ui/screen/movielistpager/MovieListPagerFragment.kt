package com.example.moviedb.ui.screen.movielistpager

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.moviedb.R
import com.example.moviedb.data.constants.MovieListType
import com.example.moviedb.databinding.FragmentMovieListPagerBinding
import com.example.moviedb.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_list_pager.*

@AndroidEntryPoint
class MovieListPagerFragment :
    BaseFragment<FragmentMovieListPagerBinding, MovieListPagerViewModel>() {

    companion object {
        const val TAG = "MovieListPagerFragment"
    }

    override val layoutId: Int = R.layout.fragment_movie_list_pager

    override val viewModel: MovieListPagerViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val movieListPagerAdapter = MovieListPagerAdapter(
            ArrayList<MovieListType>().apply {
                add(MovieListType.POPULAR)
                add(MovieListType.TOP_RATED)
            }, this
        )

        movie_list_pager?.apply {
            clipToPadding = false
            clipChildren = false
            // retain 1 page on each size
            offscreenPageLimit = 1
            orientation = ViewPager2.ORIENTATION_VERTICAL

            adapter = movieListPagerAdapter
        }

    }
}