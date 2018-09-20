package com.quanda.moviedb.di

import com.quanda.moviedb.ui.screen.favoritemovie.FavoriteMovieFragment
import com.quanda.moviedb.ui.screen.login.LoginFragment
import com.quanda.moviedb.ui.screen.main.MainFragment
import com.quanda.moviedb.ui.screen.movie.MovieListFragment
import com.quanda.moviedb.ui.screen.moviedetail.MovieDetailFragment
import com.quanda.moviedb.ui.screen.permission.PermissionFragment
import com.quanda.moviedb.ui.screen.popularmovie.PopularMovieFragment
import com.quanda.moviedb.ui.screen.tv.TvListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributePopularMovieFragment(): PopularMovieFragment

    @ContributesAndroidInjector
    abstract fun contributeFavoriteMovieFragment(): FavoriteMovieFragment

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieListFragment(): MovieListFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieDetailFragment(): MovieDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeMoviePermissionFragment(): PermissionFragment

    @ContributesAndroidInjector
    abstract fun contributeTvListFragment(): TvListFragment
}

