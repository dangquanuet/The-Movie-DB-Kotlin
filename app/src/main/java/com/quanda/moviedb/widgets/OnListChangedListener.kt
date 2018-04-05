package com.quanda.moviedb.widgets

import android.databinding.ObservableList
import android.support.v7.widget.RecyclerView

class OnListChangedListener<Item>(
        val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) : ObservableList.OnListChangedCallback<ObservableList<Item>>() {

    override fun onChanged(sender: ObservableList<Item>) {
        adapter.notifyDataSetChanged()
    }

    override fun onItemRangeRemoved(sender: ObservableList<Item>, positionStart: Int,
            itemCount: Int) {
        adapter.notifyItemRangeRemoved(positionStart, itemCount)
    }

    override fun onItemRangeMoved(sender: ObservableList<Item>, fromPosition: Int, toPosition: Int,
            itemCount: Int) {
        adapter.notifyDataSetChanged()
    }

    override fun onItemRangeInserted(sender: ObservableList<Item>, positionStart: Int,
            itemCount: Int) {
        adapter.notifyItemRangeInserted(positionStart, itemCount)
    }

    override fun onItemRangeChanged(sender: ObservableList<Item>, positionStart: Int,
            itemCount: Int) {
        adapter.notifyItemRangeChanged(positionStart, itemCount)
    }
}