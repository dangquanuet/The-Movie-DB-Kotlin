package com.quanda.moviedb.ui.screen.tv2

import android.databinding.DataBindingUtil
import android.support.v7.recyclerview.extensions.AsyncDifferConfig
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.quanda.moviedb.R
import com.quanda.moviedb.data.model.Tv
import com.quanda.moviedb.databinding.ItemTvBinding
import com.quanda.moviedb.ui.base.BaseViewHolderBinding
import java.util.concurrent.Executors

class TvListAdapter2(list: List<Tv>, val listener: BaseViewHolderBinding.OnItemCLickListener<Tv>?,
        callBack: DiffUtil.ItemCallback<Tv>)
    : ListAdapter<Tv, TvListAdapter2.TvHolder2>(AsyncDifferConfig.Builder<Tv>(callBack)
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
        .build()
) {

    init {
        submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvHolder2 {
        return TvHolder2(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_tv, parent, false))
    }

    override fun onBindViewHolder(holder: TvHolder2, position: Int) {
        holder.bindData(getItem(position))
    }

    class Callback : DiffUtil.ItemCallback<Tv>() {
        override fun areItemsTheSame(oldItem: Tv?, newItem: Tv?): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Tv?, newItem: Tv?): Boolean {
            return oldItem == newItem
        }
    }

    inner class TvHolder2(binding: ItemTvBinding) : BaseViewHolderBinding<ItemTvBinding, Tv>(
            binding) {
        override fun bindData(item: Tv) {
            binding.apply {
                title = item.name
                imageUrl = item.poster_path
                imageClickListener = android.view.View.OnClickListener {
                    //                    listener?.onItemClick(adapterPosition, item)
                }
            }
        }
    }
}