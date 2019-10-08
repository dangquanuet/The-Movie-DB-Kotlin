package com.example.moviedb.ui.screen.moviedetail

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentMovieDetailBinding
import com.example.moviedb.ui.base.BaseFragment
import com.example.moviedb.utils.setSingleClick
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding, MovieDetailViewModel>() {

    override val layoutId: Int = R.layout.fragment_movie_detail

    override val viewModel: MovieDetailViewModel by viewModel()

    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        button_favorite?.setOnClickListener {
            viewModel.favoriteMovie()
        }
        image_back?.setSingleClick {
            findNavController().navigateUp()
        }
        image_backdrop?.setSingleClick {
            viewModel.movie.value?.backdrop_path?.let {
                toFullImage(it)
            }
        }

        viewModel.apply {
            args.movie.let {
                movie.value = it
                checkFavorite(it.id)
                getCastAndCrew(it.id)
            }

            val castAdapter = CastAdapter(itemClickListener = {
                it.profile_path?.let { profilePath ->
                    toFullImage(profilePath)
                }
            })
            recycler_cast?.adapter = castAdapter
            cast.observe(viewLifecycleOwner, Observer {
                castAdapter.submitList(it)
            })
        }
    }

    private fun toFullImage(imageUrl: String) {
        findNavController().navigate(
            MovieDetailFragmentDirections.toImage(imageUrl)
        )
    }
}