package com.example.moviedb.ui.screen.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.moviedb.R
import com.example.moviedb.data.constants.MovieListType
import com.example.moviedb.databinding.FragmentMainBinding
import com.example.moviedb.ui.base.BaseFragment
import com.example.moviedb.ui.screen.favoritemovie.FavoriteMovieFragment
import com.example.moviedb.ui.screen.popularmovie.PopularMovieFragment
import com.example.moviedb.ui.screen.tv.TvListFragment
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.viewModel

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {

    companion object {
        const val TAG = "MainFragment"
        const val FRAGMENT_TAG = "FRAGMENT_TAG"

        fun newInstance() = MainFragment()
    }

    override val layoutId: Int = R.layout.fragment_main

    override val viewModel: MainViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        layout_tab_popular.setOnClickListener {
            onClickBottomNavigationItem(Tab.POPULAR.position)
        }
        layout_tab_rated.setOnClickListener {
            onClickBottomNavigationItem(Tab.TOP_RATED.position)
        }
        layout_tab_favorite.setOnClickListener {
            onClickBottomNavigationItem(Tab.FAVORITE.position)
        }
        layout_tab_profile.setOnClickListener {
            onClickBottomNavigationItem(Tab.PROFILE.position)
        }
        onClickBottomNavigationItem(Tab.POPULAR.position)
    }

    private fun onClickBottomNavigationItem(position: Int): Boolean {
        val currentTag = getTabFragmentTag(viewModel.currentTab.value ?: Tab.POPULAR.position)
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
        viewModel.currentTab.value = position
        fragmentTransaction.commit()

        return true
    }

    fun getTabFragmentTag(position: Int) = FRAGMENT_TAG + position

    fun newFragmentInstance(position: Int): Fragment {
        return when (position) {
            Tab.POPULAR.position -> Fragment()/*PopularMovieFragment.newInstance(
                MovieListType.POPULAR.type
            )*/
            Tab.TOP_RATED.position -> Fragment()/*PopularMovieFragment.newInstance(
                MovieListType.TOP_RATED.type
            )*/
            Tab.FAVORITE.position -> FavoriteMovieFragment.newInstance()
            Tab.PROFILE.position -> TvListFragment.newInstance()
            else -> Fragment()
        }
    }

    override fun onBack(): Boolean {
        val currentFragment = childFragmentManager.findFragmentByTag(
            getTabFragmentTag(viewModel.currentTab.value ?: Tab.POPULAR.position)
        ) ?: return false

        val stackCount = currentFragment.childFragmentManager.backStackEntryCount
        if (stackCount > 0) {
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