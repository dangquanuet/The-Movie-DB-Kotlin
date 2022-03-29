package com.example.moviedb.ui.screen.moviedetail

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.navArgs
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentMovieDetailBinding
import com.example.moviedb.ui.base.BaseFragment
import com.example.moviedb.ui.base.getNavController
import com.example.moviedb.utils.setSingleClick
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding, MovieDetailViewModel>() {

    override val layoutId: Int = R.layout.fragment_movie_detail

    override val viewModel: MovieDetailViewModel by viewModels()

    private val args: MovieDetailFragmentArgs by navArgs()

    private val castAdapter = CastAdapter(itemClickListener = { imageView, cast ->
        cast.getFullProfilePath()?.let {
            toFullImage(imageView, it)
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.buttonFavorite.setOnClickListener {
            viewModel.favoriteMovie()
        }
        viewBinding.imageBack.setSingleClick {
            getNavController()?.navigateUp()
        }
        viewBinding.imageBackdrop.setSingleClick {
            viewModel.movie.value?.getFullBackdropPath()?.let {
                toFullImage(viewBinding.imageBackdrop, it)
            }
        }

        viewModel.apply {
            args.movie.let {
                movie.value = it
                checkFavorite(it.id)
                loadCastAndCrew(it.id)
            }
        }

        if (viewBinding.recyclerCast.adapter == null) {
            viewBinding.recyclerCast.adapter = castAdapter
        }

        viewModel.apply {
            cast.observe(viewLifecycleOwner) {
                castAdapter.submitList(it)
            }
        }
    }

    private fun toFullImage(imageView: ImageView, imageUrl: String) {
        getNavController()?.navigate(
            MovieDetailFragmentDirections.toImage(imageUrl),
            FragmentNavigatorExtras(
                imageView to imageUrl
            )
        )
    }
}
