package com.quanda.moviedb.di

import com.quanda.moviedb.ui.screen.favoritemovie.FavoriteMovieFragment
import com.quanda.moviedb.ui.screen.popularmovie.PopularMovieFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributePopularMovieFragment(): PopularMovieFragment

    @ContributesAndroidInjector
    abstract fun contributeFavoriteMovieFragment(): FavoriteMovieFragment
}

