package com.quanda.moviedb.ui.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import java.util.concurrent.Executors


abstract class BaseRecyclerAdapter<Item, ViewBinding : ViewDataBinding>(
        callBack: DiffUtil.ItemCallback<Item>
) : ListAdapter<Item, BaseViewHolder<ViewBinding>>(
        AsyncDifferConfig.Builder<Item>(callBack)
                .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
                .build()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding> {
        return BaseViewHolder(createBinding(parent, viewType))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding>, position: Int) {
        bind(holder.binding, getItem(position))
        holder.binding.executePendingBindings()
    }

    protected abstract fun createBinding(parent: ViewGroup, viewType: Int): ViewBinding

    protected abstract fun bind(binding: ViewBinding, item: Item)

}

open class BaseViewHolder<ViewBinding : ViewDataBinding> constructor(
        val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)