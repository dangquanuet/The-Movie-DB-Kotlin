package com.quanda.moviedb.ui.screen.tv

import androidx.recyclerview.widget.DiffUtil
import com.quanda.moviedb.R
import com.quanda.moviedb.data.model.Tv
import com.quanda.moviedb.databinding.ItemTvBinding
import com.quanda.moviedb.ui.base.BaseRecyclerAdapter2

class TvListAdapter(
        val itemClickListener: ((Tv) -> Unit)? = null
) : BaseRecyclerAdapter2<Tv, ItemTvBinding>(object : DiffUtil.ItemCallback<Tv>() {
    override fun areItemsTheSame(oldItem: Tv, newItem: Tv): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Tv, newItem: Tv): Boolean {
        return oldItem == newItem
    }
}) {

    override fun getLayoutRes(viewType: Int): Int {
        return R.layout.item_tv
    }

    override fun bindFirstTime(binding: ItemTvBinding) {
        binding.apply {
            root.setOnClickListener {
                item?.apply {
                    itemClickListener?.invoke(this)
                }
            }
        }
    }

    override fun bindView(binding: ItemTvBinding, item: Tv, position: Int) {
        binding.apply {
            this.item = item
        }
    }
}