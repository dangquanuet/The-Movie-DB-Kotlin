package com.quanda.moviedb.base

import android.content.Context
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

abstract class BaseRecyclerViewAdapterBinding<ViewHolder : BaseViewHolderBinding<View, Item>, View : ViewDataBinding, Item>(
        val context: Context,
        val list: List<Item>) : RecyclerView.Adapter<BaseViewHolderBinding<View, Item>>() {

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder

    override fun onCreateViewHolder(parent: ViewGroup,
            viewType: Int): BaseViewHolderBinding<View, Item> = getViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: BaseViewHolderBinding<View, Item>,
            position: Int) {
        holder.bindData(list.get(position))
    }

    override fun getItemCount(): Int = list.size
}