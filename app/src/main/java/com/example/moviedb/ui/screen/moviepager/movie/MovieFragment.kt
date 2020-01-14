package com.example.moviedb.ui.screen.moviepager.movie

import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentMovieBinding
import com.example.moviedb.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment: BaseFragment<FragmentMovieBinding, MovieViewModel>() {

    override val layoutId: Int = R.layout.fragment_movie

    override val viewModel: MovieViewModel by viewModel()

}