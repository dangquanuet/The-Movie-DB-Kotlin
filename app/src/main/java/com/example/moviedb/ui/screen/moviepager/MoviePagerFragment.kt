package com.example.moviedb.ui.screen.moviepager

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.moviedb.R
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.FragmentMoviePagerBinding
import com.example.moviedb.ui.base.BaseLoadMoreRefreshFragment
import kotlinx.android.synthetic.main.fragment_movie_pager.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs


class MoviePagerFragment :
    BaseLoadMoreRefreshFragment<FragmentMoviePagerBinding, MoviePagerViewModel, Movie>() {

    companion object {
        const val TAG = "MoviePagerFragment"
        const val TYPE = "TYPE"
        private const val MAX_SCALE = 0.76f
        private const val SCALE_PERCENT = 0.8f
        private const val MIN_SCALE = SCALE_PERCENT * MAX_SCALE
        private const val MAX_ALPHA = 1.0f
        private const val MIN_ALPHA = 0.3f

        fun newInstance(type: Int) = MoviePagerFragment().apply {
            arguments = Bundle().apply {
                putInt(TYPE, type)
            }
        }
    }

    override val layoutId: Int = R.layout.fragment_movie_pager

    override val viewModel: MoviePagerViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*viewModel.apply {
            mode.value = args.type?.toInt()
        }*/

        val adapter = MoviePagerAdapter(
            itemClickListener = { toMovieDetail(it) }
        )

        container.setBackgroundColor(Color.BLACK)
        viewPager2?.apply {
            clipToPadding = false
            clipChildren = false
            // retain 1 page on each size
            offscreenPageLimit = 1
            this.adapter = adapter

            val nextItemVisiblePx = resources.getDimensionPixelOffset(R.dimen.dp_100)
            val currentItemHorizontalMarginPx = resources.getDimensionPixelOffset(R.dimen.dp_50)
            setPageTransformer { view, position ->
                // position  -1: left, 0: center, 1: right
                val absPosition = abs(position)
                // alpha from 0.3 to 1.0
                view.alpha = MAX_ALPHA - (MAX_ALPHA - MIN_ALPHA) * absPosition
                // scaleY from 0.7 to 0.9
                val scaleY = MAX_SCALE - (MAX_SCALE - MIN_SCALE) * absPosition
                view.scaleY = scaleY
                // translation X
                view.translationX = -position * (nextItemVisiblePx + currentItemHorizontalMarginPx)
                // translation Y
                view.translationY = abs(position) * ((MAX_SCALE - scaleY) / 2 * view.height)
            }
        }

        viewModel.apply {
            listItem.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })
            firstLoad()
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

    private fun toMovieDetail(movie: Movie) {
    }
}