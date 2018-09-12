package com.quanda.moviedb.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.quanda.moviedb.ui.screen.MainActivityViewModel
import com.quanda.moviedb.ui.screen.favoritemovie.FavoriteMovieViewModel
import com.quanda.moviedb.ui.screen.login.LoginViewModel
import com.quanda.moviedb.ui.screen.main.MainViewModel
import com.quanda.moviedb.ui.screen.moviedetail.MovieDetailViewModel
import com.quanda.moviedb.ui.screen.popularmovie.PopularMovieViewModel
import com.quanda.moviedb.ui.screen.tv.TvListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(
            providerFactory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteMovieViewModel::class)
    abstract fun bindFavoriteMovieViewModel(
            favoriteMovieViewModel: FavoriteMovieViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    abstract fun bindMovieDetailViewModel(movieDetailViewModel: MovieDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PopularMovieViewModel::class)
    abstract fun bindPopularMovieViewModel(popularMovieViewModel: PopularMovieViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TvListViewModel::class)
    abstract fun bindPopularTvListViewModel(tvListViewModel: TvListViewModel): ViewModel
}