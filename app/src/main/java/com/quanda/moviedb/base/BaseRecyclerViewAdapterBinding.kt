package com.quanda.moviedb.base

import android.content.Context
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

abstract class BaseRecyclerViewAdapterBinding<X : ViewDataBinding, T : BaseViewHolderBinding<X, K>, K>(
        val context: Context,
        val list: List<K>) : RecyclerView.Adapter<BaseViewHolderBinding<X, K>>() {

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): T

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolderBinding<X, K> {
        return getViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolderBinding<X, K>, position: Int) {
        holder.bindData(list.get(position))
    }

    override fun getItemCount(): Int {
        return list.size
    }
}