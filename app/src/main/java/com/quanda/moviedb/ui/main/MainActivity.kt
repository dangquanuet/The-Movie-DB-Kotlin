package com.quanda.moviedb.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.quanda.moviedb.R
import com.quanda.moviedb.base.activity.BaseDataLoadActivity
import com.quanda.moviedb.constants.BundleConstants
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.databinding.ActivityMainBinding
import com.quanda.moviedb.ui.main.moviedetail.MovieDetailActivity
import com.quanda.moviedb.ui.main.popularmovie.PopularMovieFragment
import com.quanda.moviedb.ui.main.popularmovie.PopularMovieNavigator
import com.quanda.moviedb.utils.goToActivity

class MainActivity : BaseDataLoadActivity<ActivityMainBinding, MainViewModel>(), MainNavigator, PopularMovieNavigator {

    companion object {
        const val FRAGMENT_TAG = "fragment_tag_"
    }

    lateinit var bottomNavigation: AHBottomNavigation
    var currentPositionFragment = Tab.POPULAR.position

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initViewModel(): MainViewModel {
        return ViewModelProviders.of(this, MainViewModel.CustomFactory(application, this)).get(
                MainViewModel::class.java)
    }

    override fun initData() {
        super.initData()
        binding.viewModel = viewModel

        initBottomNavigation()
    }

    fun initBottomNavigation() {
        bottomNavigation = binding.bottomNavigation
        val bottomNavigationAdapter = AHBottomNavigationAdapter(this@MainActivity,
                R.menu.menu_bottom_navigation)
        bottomNavigationAdapter.setupWithBottomNavigation(bottomNavigation)

        bottomNavigation.apply {
            titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
            defaultBackgroundColor = ContextCompat.getColor(this@MainActivity, R.color.white)

            setOnTabSelectedListener({ position, wasSelected ->
                onClickBottomNavigationItem(position)
            })
            currentItem = Tab.POPULAR.position
        }
    }

    fun onClickBottomNavigationItem(position: Int): Boolean {
        val currentTag = getTabFragmentTag(currentPositionFragment)
        val newTag = getTabFragmentTag(position)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val currentFragment = fragmentManager.findFragmentByTag(currentTag)
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment)
        }

        var newFragment: Fragment? = fragmentManager.findFragmentByTag(newTag)
        if (newFragment == null) {
            newFragment = newFragmentInstance(position)
            if (newFragment.isAdded()) {
                fragmentTransaction.show(newFragment)
            } else {
                fragmentTransaction.add(R.id.frame_layout, newFragment, newTag)
            }
        } else {
            fragmentTransaction.show(newFragment)
        }
        currentPositionFragment = position
        fragmentTransaction.commit()

        return true
    }

    fun getTabFragmentTag(position: Int) = FRAGMENT_TAG + position

    fun newFragmentInstance(position: Int): Fragment {
        return when (position) {
            Tab.POPULAR.position -> PopularMovieFragment.newInstance(
                    PopularMovieFragment.TYPE.POPULAR.type)
            Tab.TOP_RATED.position -> PopularMovieFragment.newInstance(
                    PopularMovieFragment.TYPE.TOP_RATED.type)
            Tab.FAVORITE.position -> Fragment() // TODO
            Tab.PROFILE.position -> Fragment() // TODO
            else -> Fragment()
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame_layout)
        if (fragment != null) {
            if (fragment.activity == null || fragment.activity!!.isFinishing) return
            val stackCount = fragment.childFragmentManager.backStackEntryCount
            if (stackCount <= 1) {
                fragment.activity?.finish()
            } else {
                fragment.childFragmentManager.popBackStackImmediate()
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun goToMovieDetail(movie: Movie) {
        val bundle = Bundle()
        bundle.putParcelable(BundleConstants.MOVIE, movie)
        goToActivity(MovieDetailActivity::class.java, bundle)
    }

    enum class Tab(val position: Int) {
        POPULAR(0), TOP_RATED(1), FAVORITE(2), PROFILE(3)
    }
}
