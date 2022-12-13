package com.example.moviedb.ui.screen.movielistpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviedb.data.constant.MovieListType
import com.example.moviedb.ui.screen.moviepager.MoviePagerFragment

class MovieListPagerAdapter(
    private val typeList: ArrayList<MovieListType>,
    val fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return typeList.size
    }

    override fun createFragment(position: Int): Fragment {
        return MoviePagerFragment.newInstance(typeList[position].type, position)
    }
}
