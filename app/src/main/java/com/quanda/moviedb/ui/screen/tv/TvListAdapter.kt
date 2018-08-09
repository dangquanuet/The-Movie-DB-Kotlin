package com.quanda.moviedb.ui.screen.tv

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.quanda.moviedb.R
import com.quanda.moviedb.data.model.Tv
import com.quanda.moviedb.databinding.ItemTvBinding
import com.quanda.moviedb.ui.base.BaseRecyclerAdapter

class TvListAdapter(
        val itemClickListener: ((Tv) -> Unit)? = null
) : BaseRecyclerAdapter<Tv, ItemTvBinding>(object : DiffUtil.ItemCallback<Tv>() {
    override fun areItemsTheSame(oldItem: Tv?, newItem: Tv?): Boolean {
        return oldItem?.id == newItem?.id
    }

    override fun areContentsTheSame(oldItem: Tv?, newItem: Tv?): Boolean {
        return oldItem == newItem
    }
}) {

    override fun createBinding(parent: ViewGroup, viewType: Int): ItemTvBinding {
        return DataBindingUtil.inflate<ItemTvBinding>(LayoutInflater.from(parent.context),
                R.layout.item_tv, parent, false).apply {
            root.setOnClickListener {
                item?.apply {
                    itemClickListener?.invoke(this)
                }
            }
        }
    }

    override fun bind(binding: ItemTvBinding, item: Tv) {
        binding.apply {
            this.item = item
        }
    }
}