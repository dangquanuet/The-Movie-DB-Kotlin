package com.example.moviedb.ui.screen.moviepager

import android.graphics.Color
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.example.moviedb.R
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.FragmentMoviePagerBinding
import com.example.moviedb.ui.base.BaseListAdapter
import com.example.moviedb.ui.base.BaseLoadMoreRefreshFragment
import kotlinx.android.synthetic.main.fragment_movie_pager.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs


class MoviePagerFragment :
    BaseLoadMoreRefreshFragment<FragmentMoviePagerBinding, MoviePagerViewModel, Movie>() {

    companion object {
        const val TAG = "MoviePagerFragment"
        const val TYPE = "TYPE"
        const val POSITION = "POSITION"
        private const val MAX_SCALE = 1f
        private const val SCALE_PERCENT = 0.8f
        private const val MIN_SCALE = SCALE_PERCENT * MAX_SCALE
        private const val MAX_ALPHA = 1.0f
        private const val MIN_ALPHA = 0.3f

        fun newInstance(type: Int, position: Int) = MoviePagerFragment().apply {
            arguments = Bundle().apply {
                putInt(TYPE, type)
                putInt(POSITION, position)
            }
        }
    }

    override val layoutId: Int = R.layout.fragment_movie_pager

    override val viewModel: MoviePagerViewModel by viewModel()

    override val listAdapter: BaseListAdapter<Movie, out ViewDataBinding> by lazy {
        MoviePagerAdapter(
            itemClickListener = { toMovieDetail(it) }
        )
    }

    override fun setupLoadMoreRefresh() {
        // do nothing
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.apply {
            mode.value = arguments?.getInt(TYPE)
        }

        container.setBackgroundColor(Color.BLACK)
        movie_pager?.apply {
            clipToPadding = false
            clipChildren = false
            // retain 1 page on each size
            offscreenPageLimit = 1
            this.adapter = listAdapter
            val screenHeight = resources.displayMetrics.heightPixels
            val nextItemTranslationX = 19f * screenHeight / 60
            setPageTransformer { view, position ->
                // position  -1: left, 0: center, 1: right
                val absPosition = abs(position)
                // alpha from MIN_ALPHA to MAX_ALPHA
                view.alpha = MAX_ALPHA - (MAX_ALPHA - MIN_ALPHA) * absPosition
                // scale from MIN_SCALE to MAX_SCALE
                val scale = MAX_SCALE - (MAX_SCALE - MIN_SCALE) * absPosition
                view.scaleY = scale
                view.scaleX = scale
                // translation X
                view.translationX = -position * nextItemTranslationX
                // translation Y
//                view.translationY = abs(position) * ((MAX_SCALE - scaleY) / 2 * view.height)
            }
        }

        viewModel.apply {
            itemList.observe(viewLifecycleOwner, Observer {
                listAdapter.submitList(it)
            })
            firstLoad()
        }
    }

    private fun toMovieDetail(movie: Movie) {
    }
}