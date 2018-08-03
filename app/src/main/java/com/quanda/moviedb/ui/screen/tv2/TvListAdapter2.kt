package com.quanda.moviedb.ui.screen.tv2

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.quanda.moviedb.R
import com.quanda.moviedb.data.model.Tv
import com.quanda.moviedb.databinding.ItemTvBinding
import com.quanda.moviedb.ui.base.BaseRecyclerAdapter

class TvListAdapter2 : BaseRecyclerAdapter<Tv, ItemTvBinding>(object : DiffUtil.ItemCallback<Tv>() {
    override fun areItemsTheSame(oldItem: Tv?, newItem: Tv?): Boolean {
        return oldItem?.id == newItem?.id
    }

    override fun areContentsTheSame(oldItem: Tv?, newItem: Tv?): Boolean {
        return oldItem == newItem
    }
}) {

    override fun createBinding(parent: ViewGroup, viewType: Int): ItemTvBinding {
        val binding: ItemTvBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_tv, parent, false)
        binding.root.setOnClickListener {
            binding.item?.apply {
                itemClickListener?.invoke(this)
            }
        }
        return binding
    }

    override fun bind(binding: ItemTvBinding, item: Tv) {
        binding.apply {
            this.item = item
        }
    }
}