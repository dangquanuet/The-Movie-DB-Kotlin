package com.quanda.moviedb.di

import com.quanda.moviedb.ui.screen.main.MainViewModel
import com.quanda.moviedb.ui.screen.main.favoritemovie.FavoriteMovieViewModel
import com.quanda.moviedb.ui.screen.main.login.LoginViewModel
import com.quanda.moviedb.ui.screen.main.moviedetail.MovieDetailViewModel
import com.quanda.moviedb.ui.screen.main.popularmovie.PopularMovieViewModel
import com.quanda.moviedb.ui.screen.movie.MovieListViewModel
import com.quanda.moviedb.ui.screen.tv.TvListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, ApiModule::class))
interface AppComponent {

    fun inject(viewModel: MainViewModel)

    fun inject(viewModel: PopularMovieViewModel)

    fun inject(viewModel: FavoriteMovieViewModel)

    fun inject(viewModel: MovieListViewModel)

    fun inject(viewModel: TvListViewModel)

    fun inject(viewModel: MovieDetailViewModel)

    fun inject(viewModel: LoginViewModel)
}