package com.example.moviedb.ui.screen.popularmovie

import androidx.recyclerview.widget.DiffUtil
import com.example.moviedb.R
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.ItemMovieBinding
import com.example.moviedb.ui.base.BaseListAdapter
import com.example.moviedb.ui.base.BaseViewHolder
import com.example.moviedb.utils.setSingleClick

class PopularMovieAdapter(
    val itemClickListener: (Movie) -> Unit = {},
    val onBindPosition: (Int) -> Unit = {}
) : BaseListAdapter<Movie, ItemMovieBinding>(object : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}) {

    override fun getLayoutRes(viewType: Int): Int {
        return R.layout.item_movie
    }

    override fun bindFirstTime(binding: ItemMovieBinding) {
        binding.apply {
            root.setSingleClick {
                item?.apply {
                    itemClickListener(this)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ItemMovieBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        onBindPosition(position)
    }
}