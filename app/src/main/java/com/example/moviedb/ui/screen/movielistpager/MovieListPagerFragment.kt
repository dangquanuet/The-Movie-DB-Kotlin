package com.example.moviedb.ui.screen.movielistpager

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.moviedb.R
import com.example.moviedb.data.constants.MovieListType
import com.example.moviedb.databinding.FragmentMovieListPagerBinding
import com.example.moviedb.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_movie_list_pager.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieListPagerFragment :
    BaseFragment<FragmentMovieListPagerBinding, MovieListPagerViewModel>() {

    companion object {
        const val TAG = "MovieListPagerFragment"
    }

    override val layoutId: Int = R.layout.fragment_movie_list_pager

    override val viewModel: MovieListPagerViewModel by viewModel()

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

    override fun onStart() {
        super.onStart()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    override fun onStop() {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onStop()
    }
}