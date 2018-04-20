package com.quanda.moviedb.di

import com.quanda.moviedb.ui.main.moviedetail.MovieDetailViewModel
import com.quanda.moviedb.ui.main.popularmovie.PopularMovieViewModel
import com.quanda.moviedb.ui.movie.MovieListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, ApiModule::class))
interface AppComponent {

    fun inject(viewModel: PopularMovieViewModel)

    fun inject(viewModel: MovieListViewModel)

    fun inject(viewModel: MovieDetailViewModel)

    interface Injectable {
        fun inject(appComponent: AppComponent)
    }
}