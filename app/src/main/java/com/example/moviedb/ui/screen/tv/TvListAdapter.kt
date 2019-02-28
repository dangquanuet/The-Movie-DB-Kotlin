package com.example.moviedb.ui.screen.tv

import androidx.recyclerview.widget.DiffUtil
import com.example.moviedb.R
import com.example.moviedb.data.model.Tv
import com.example.moviedb.databinding.ItemTvBinding
import com.example.moviedb.ui.base.BaseRecyclerAdapter

class TvListAdapter(
    val itemClickListener: (Tv) -> Unit = {}
) : BaseRecyclerAdapter<Tv, ItemTvBinding>(object : DiffUtil.ItemCallback<Tv>() {
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
                    itemClickListener(this)
                }
            }
        }
    }

    override fun bindView(binding: ItemTvBinding, item: Tv, position: Int) {
        binding.apply {

        }
    }
}