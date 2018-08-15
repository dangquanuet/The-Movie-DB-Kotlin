package com.quanda.moviedb.ui.screen.popularmovie

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.quanda.moviedb.R
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.databinding.ItemMovieBinding
import com.quanda.moviedb.ui.base.BaseRecyclerAdapter

class PopularMovieAdapter(
        val itemClickListener: ((Movie) -> Unit)? = null
) : BaseRecyclerAdapter<Movie, ItemMovieBinding>(object : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}) {

    override fun createBinding(parent: ViewGroup, viewType: Int): ItemMovieBinding {
        return DataBindingUtil.inflate<ItemMovieBinding>(LayoutInflater.from(parent.context),
                R.layout.item_movie, parent, false).apply {
            root.setOnClickListener {
                item?.apply {
                    itemClickListener?.invoke(this)
                }
            }
        }
    }

    override fun bind(binding: ItemMovieBinding, item: Movie) {
        binding.apply {
            this.item = item
        }
    }
}