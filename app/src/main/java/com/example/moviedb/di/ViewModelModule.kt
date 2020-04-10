package com.example.moviedb.di

import com.example.moviedb.ui.navigation.favoritecontainer.FavoriteContainerVIewModel
import com.example.moviedb.ui.navigation.popularcontainer.PopularContainerViewModel
import com.example.moviedb.ui.screen.MainActivityViewModel
import com.example.moviedb.ui.screen.favoritemovie.FavoriteMovieViewModel
import com.example.moviedb.ui.screen.image.ImageViewModel
import com.example.moviedb.ui.screen.login.LoginViewModel
import com.example.moviedb.ui.screen.main.MainViewModel
import com.example.moviedb.ui.screen.moviedetail.MovieDetailViewModel
import com.example.moviedb.ui.screen.movielistpager.MovieListPagerViewModel
import com.example.moviedb.ui.screen.moviepager.MoviePagerViewModel
import com.example.moviedb.ui.screen.moviepager.movie.MovieViewModel
import com.example.moviedb.ui.screen.oldmain.OldMainViewModel
import com.example.moviedb.ui.screen.permission.PermissionViewModel
import com.example.moviedb.ui.screen.popularmovie.PopularMovieViewModel
import com.example.moviedb.ui.screen.profile.ProfileViewModel
import com.example.moviedb.ui.screen.splash.SplashViewModel
import com.example.moviedb.ui.screen.tv.TvListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainActivityViewModel() }
    viewModel { OldMainViewModel() }
    viewModel { FavoriteMovieViewModel(get()) }
    viewModel { LoginViewModel() }
    viewModel { MovieDetailViewModel(get()) }
    viewModel { PopularMovieViewModel(get()) }
    viewModel { TvListViewModel(get()) }
    viewModel { PopularContainerViewModel() }
    viewModel { FavoriteContainerVIewModel() }
    viewModel { SplashViewModel() }
    viewModel { MainViewModel() }
    viewModel { ProfileViewModel() }
    viewModel { PermissionViewModel() }
    viewModel { ImageViewModel() }
    viewModel { MoviePagerViewModel(get(), get()) }
    viewModel { MovieListPagerViewModel() }
    viewModel { MovieViewModel() }
}