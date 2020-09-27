package com.example.moviedb.ui.screen.moviepager.movie

import androidx.fragment.app.viewModels
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentMovieBinding
import com.example.moviedb.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment: BaseFragment<FragmentMovieBinding, MovieViewModel>() {

    override val layoutId: Int = R.layout.fragment_movie

    override val viewModel: MovieViewModel by viewModels()

}