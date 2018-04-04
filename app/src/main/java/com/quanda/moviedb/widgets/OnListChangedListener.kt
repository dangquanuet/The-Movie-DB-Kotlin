package com.quanda.moviedb.widgets

import android.databinding.ObservableList
import android.support.v7.widget.RecyclerView

class OnListChangedListener<List : ObservableList<Item>, Item>(
        private val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) : ObservableList.OnListChangedCallback<List>() {
    override fun onChanged(sender: List) {
        adapter.notifyDataSetChanged()
    }

    override fun onItemRangeRemoved(sender: List, positionStart: Int, itemCount: Int) {
        adapter.notifyItemRangeRemoved(positionStart, itemCount)
    }

    override fun onItemRangeMoved(sender: List, fromPosition: Int, toPosition: Int,
            itemCount: Int) {
        adapter.notifyDataSetChanged()
    }

    override fun onItemRangeInserted(sender: List, positionStart: Int, itemCount: Int) {
        adapter.notifyItemRangeInserted(positionStart, itemCount)
    }

    override fun onItemRangeChanged(sender: List, positionStart: Int, itemCount: Int) {
        adapter.notifyItemRangeChanged(positionStart, itemCount)
    }
}