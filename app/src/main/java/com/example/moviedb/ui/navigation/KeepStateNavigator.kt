package com.example.moviedb.ui.navigation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator

/**
 * https://github.com/STAR-ZERO/navigation-keep-fragment-sample/blob/master/app/src/main/java/com/star_zero/navigation_keep_fragment_sample/navigation/KeepStateNavigator.kt
 */

// archived
@Navigator.Name("keep_state_fragment") // `keep_state_fragment` is used in navigation xml
class KeepStateNavigator(
    private val context: Context,
    private val fragmentManager: FragmentManager, // Should pass childFragmentManager.
    private val containerId: Int
) : FragmentNavigator(context, fragmentManager, containerId) {

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        val tag = destination.id.toString()
        val fragmentTransaction = fragmentManager.beginTransaction()

        var initialNavigate = false
        val currentFragment = fragmentManager.primaryNavigationFragment
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment)
        } else {
            initialNavigate = true
        }

        var newFragment: Fragment? = fragmentManager.findFragmentByTag(tag)
        if (newFragment == null) {
            val className = destination.className
            newFragment =
                fragmentManager.fragmentFactory.instantiate(context.classLoader, className)
            if (newFragment.isAdded) {
                fragmentTransaction.show(newFragment)
            } else {
                fragmentTransaction.add(containerId, newFragment, tag)
            }
        } else {
            fragmentTransaction.show(newFragment)
        }

        fragmentTransaction.setPrimaryNavigationFragment(newFragment)
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.commitNow()

        return if (initialNavigate) {
            destination
        } else {
            null
        }
    }
}
