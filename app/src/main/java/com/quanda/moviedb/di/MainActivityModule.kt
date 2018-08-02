package com.quanda.moviedb.di

import com.quanda.moviedb.ui.screen.MainActivity
import com.quanda.moviedb.ui.screen.tv.TvListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeTvListActivity(): TvListActivity

}
