package com.quanda.moviedb.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quanda.moviedb.BR
import java.util.concurrent.Executors

abstract class BaseRecyclerAdapter2<Item, ViewBinding : ViewDataBinding>(
        callBack: DiffUtil.ItemCallback<Item>
) : ListAdapter<Item, BaseViewHolder2<ViewBinding>>(
        AsyncDifferConfig.Builder<Item>(callBack)
                .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
                .build()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder2<ViewBinding> {
        return BaseViewHolder2(DataBindingUtil.inflate<ViewBinding>(
                LayoutInflater.from(parent.context),
                getLayoutRes(viewType),
                parent, false).apply {
            bindFirstTime(this)
        })
    }

    override fun onBindViewHolder(holder: BaseViewHolder2<ViewBinding>, position: Int) {
        val item: Item = getItem(position)
        holder.binding.setVariable(BR.item, item)
        bindView(holder.binding, item)
        holder.binding.executePendingBindings()
    }

    /**
     * get layout res based on view type
     */
    protected abstract fun getLayoutRes(viewType: Int): Int

    /**
     * override if need
     * bind first time
     * use for set item onClickListener, something only set one time
     */
    protected open fun bindFirstTime(binding: ViewBinding) {}

    /**
     * override if need
     * bind view
     */
    protected open fun bindView(binding: ViewBinding, item: Item) {}
}

open class BaseViewHolder2<ViewBinding : ViewDataBinding> constructor(
        val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)