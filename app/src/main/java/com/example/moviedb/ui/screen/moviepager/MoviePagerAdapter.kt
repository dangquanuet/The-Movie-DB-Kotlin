package com.example.moviedb.ui.screen.moviepager

import androidx.recyclerview.widget.DiffUtil
import com.example.moviedb.R
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.ItemMoviePagerBinding
import com.example.moviedb.ui.base.BaseRecyclerAdapter
import com.example.moviedb.utils.setSingleClick

class MoviePagerAdapter(
    val itemClickListener: (Movie) -> Unit = {}
) : BaseRecyclerAdapter<Movie, ItemMoviePagerBinding>(object : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}) {

    override fun getLayoutRes(viewType: Int): Int {
        return R.layout.item_movie_pager
    }

    override fun bindFirstTime(binding: ItemMoviePagerBinding) {
        binding.apply {
            root.setSingleClick {
                item?.apply {
                    itemClickListener(this)
                }
            }
        }
    }

}