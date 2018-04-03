package com.quanda.moviedb.base

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

abstract class BaseViewHolderBinding<T : ViewDataBinding, K>(
        val binding: T) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bindData(item: K)

    interface OnItemCLickListener<K> {
        fun onItemClick(position: Int, data: K)
    }
}