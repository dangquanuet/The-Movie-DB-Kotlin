package com.example.moviedb.ui.screen.paged

import androidx.recyclerview.widget.DiffUtil
import com.example.moviedb.R
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.ItemMovieBinding
import com.example.moviedb.ui.base.BasePagedListAdapter
import com.example.moviedb.utils.setSingleClick

class PagedMovieAdapter(
    val itemClickListener: (Movie) -> Unit = {}
) : BasePagedListAdapter<Movie, ItemMovieBinding>(object : DiffUtil.ItemCallback<Movie>() {
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

}