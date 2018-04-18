package com.quanda.moviedb.ui.main.popularmovie

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quanda.moviedb.R
import com.quanda.moviedb.base.BaseRecyclerViewAdapterBinding
import com.quanda.moviedb.base.BaseViewHolderBinding
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.databinding.ItemMovieBinding

class PopularMovieAdapter(context: Context, list: List<Movie>,
        val listener: BaseViewHolderBinding.OnItemCLickListener<Movie>?) : BaseRecyclerViewAdapterBinding<PopularMovieAdapter.MovieHolder, ItemMovieBinding, Movie>(
        context, list) {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_movie,
                        parent, false))
    }

    inner class MovieHolder(
            binding: ItemMovieBinding) : BaseViewHolderBinding<ItemMovieBinding, Movie>(
            binding) {
        override fun bindData(item: Movie) {
            binding.apply {
                title = item.title
                imageUrl = item.poster_path
                imageClickListener = View.OnClickListener {
                    listener?.onItemClick(adapterPosition, item)
                }
            }
        }
    }
}