package com.marcinmoskala.kotlinacademy.ui.common.recycler

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

open class BaseRecyclerViewAdapter<T : ItemAdapter<out BaseViewHolder>>(
        initialItems: List<T>
) : RecyclerView.Adapter<BaseViewHolder>() {

    protected val items: MutableList<T> = initialItems.toMutableList()

    open fun setCustomItemViewParams(parent: ViewGroup, itemView: View) {}

    override final fun getItemCount() = items.size

    override final fun getItemViewType(position: Int) = items[position].layoutId

    override final fun onCreateViewHolder(parent: ViewGroup, layoutId: Int): BaseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        setCustomItemViewParams(parent, itemView)
        return items.first { it.layoutId == layoutId }.onCreateViewHolder(itemView, parent)
    }

    override final fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        items[position].onBindBaseViewHolder(holder)
    }
}
