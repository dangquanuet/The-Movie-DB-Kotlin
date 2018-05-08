package com.quanda.moviedb.ui.screen.tv

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quanda.moviedb.R
import com.quanda.moviedb.data.model.Tv
import com.quanda.moviedb.databinding.ItemTvBinding
import com.quanda.moviedb.ui.base.BaseRecyclerViewAdapterBinding
import com.quanda.moviedb.ui.base.BaseViewHolderBinding

class TvListAdapter(context: Context, list: List<Tv>,
        val listener: BaseViewHolderBinding.OnItemCLickListener<Tv>?) : BaseRecyclerViewAdapterBinding<TvListAdapter.TvHolder, ItemTvBinding, Tv>(
        context, list) {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): TvHolder {
        return TvHolder(
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_tv,
                        parent, false))
    }

    inner class TvHolder(binding: ItemTvBinding) : BaseViewHolderBinding<ItemTvBinding, Tv>(
            binding) {
        override fun bindData(item: Tv) {
            binding.apply {
                title = item.name
                imageUrl = item.poster_path
                imageClickListener = View.OnClickListener {
                    listener?.onItemClick(adapterPosition, item)
                }
            }
        }
    }
}