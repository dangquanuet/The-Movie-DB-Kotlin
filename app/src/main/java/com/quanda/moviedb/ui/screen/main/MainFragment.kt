package com.quanda.moviedb.ui.screen.main

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.quanda.moviedb.BR
import com.quanda.moviedb.R
import com.quanda.moviedb.data.constants.MovieListType
import com.quanda.moviedb.databinding.FragmentMainBinding
import com.quanda.moviedb.ui.base.BaseFragment
import com.quanda.moviedb.ui.screen.favoritemovie.FavoriteMovieFragment
import com.quanda.moviedb.ui.screen.popularmovie.PopularMovieFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {

    companion object {
        const val TAG = "MainFragment"
        const val FRAGMENT_TAG = "FRAGMENT_TAG"

        fun newInstance() = MainFragment()
    }

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.fragment_main

    override val viewModel by viewModel<MainViewModel>()

    lateinit var bottomNavigation: AHBottomNavigation

    var currentPositionFragment = Tab.POPULAR.position

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewBinding.viewModel = viewModel
        initBottomNavigation()
    }

    fun initBottomNavigation() {
        bottomNavigation = viewBinding.bottomNavigation
        val bottomNavigationAdapter = AHBottomNavigationAdapter(activity,
                R.menu.menu_bottom_navigation)
        bottomNavigationAdapter.setupWithBottomNavigation(bottomNavigation)

        bottomNavigation.apply {
            titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
            defaultBackgroundColor = ContextCompat.getColor(context, R.color.white)

            setOnTabSelectedListener { position, wasSelected ->
                if (position == Tab.PROFILE.position) { // TODO check login
                    //                    goToActivity(LoginActivity::class.java)
//                    goToActivity(TvListActivity::class.java)
                }
                onClickBottomNavigationItem(position)
            }
            currentItem = Tab.POPULAR.position
        }
    }

    fun onClickBottomNavigationItem(position: Int): Boolean {
        val currentTag = getTabFragmentTag(currentPositionFragment)
        val newTag = getTabFragmentTag(position)

        val fragmentManager = childFragmentManager
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

            // refresh favorite movies
            if (newFragment is FavoriteMovieFragment) {
                newFragment.loadData()
            }
        }
        currentPositionFragment = position
        fragmentTransaction.commit()

        return true
    }

    fun getTabFragmentTag(position: Int) = FRAGMENT_TAG + position

    fun newFragmentInstance(position: Int): Fragment {
        return when (position) {
            Tab.POPULAR.position -> PopularMovieFragment.newInstance(
                    MovieListType.POPULAR.type)
            Tab.TOP_RATED.position -> PopularMovieFragment.newInstance(
                    MovieListType.TOP_RATED.type)
            Tab.FAVORITE.position -> FavoriteMovieFragment.newInstance()
            Tab.PROFILE.position -> Fragment() // TODO
            else -> Fragment()
        }
    }

    override fun onBack(): Boolean {
        val currentFragment = childFragmentManager.findFragmentByTag(
                getTabFragmentTag(currentPositionFragment))
        val stackCount = currentFragment?.childFragmentManager?.backStackEntryCount
        if (stackCount != null && stackCount > 0) {
            currentFragment.childFragmentManager.popBackStack()

            // refresh favorite movies
            if (currentFragment is FavoriteMovieFragment) {
                currentFragment.loadData()
            }

            // handled in child
            return true
        }
        // not handle in child, parent will handle
        return false
    }

}