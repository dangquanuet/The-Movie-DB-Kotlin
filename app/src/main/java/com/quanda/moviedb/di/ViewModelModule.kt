package com.quanda.moviedb.di

import com.quanda.moviedb.ui.screen.MainActivityViewModel
import com.quanda.moviedb.ui.screen.favoritemovie.FavoriteMovieViewModel
import com.quanda.moviedb.ui.screen.login.LoginViewModel
import com.quanda.moviedb.ui.screen.main.MainViewModel
import com.quanda.moviedb.ui.screen.moviedetail.MovieDetailViewModel
import com.quanda.moviedb.ui.screen.popularmovie.PopularMovieViewModel
import com.quanda.moviedb.ui.screen.tv.TvListViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module(override = true) {
    viewModel { MainActivityViewModel() }
    viewModel { MainViewModel() }
    viewModel { FavoriteMovieViewModel(get()) }
    viewModel { LoginViewModel() }
    viewModel { MovieDetailViewModel(get(), get()) }
    viewModel { PopularMovieViewModel(get(), get()) }
    viewModel { TvListViewModel(get()) }
}