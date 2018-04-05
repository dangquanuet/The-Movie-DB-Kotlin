package com.quanda.moviedb.base

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

abstract class BaseViewHolderBinding<View : ViewDataBinding, Item>(
        val binding: View) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bindData(item: Item)

    interface OnItemCLickListener<Item> {
        fun onItemClick(position: Int, data: Item)
    }
}