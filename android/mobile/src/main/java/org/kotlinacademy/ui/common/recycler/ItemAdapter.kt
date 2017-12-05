package org.kotlinacademy.ui.common.recycler

import android.support.annotation.LayoutRes
import android.view.View
import android.view.ViewGroup

abstract class ItemAdapter<T : BaseViewHolder>(@LayoutRes open val layoutId: Int) {

    protected var holder: T? = null

    abstract fun onCreateViewHolder(itemView: View, parent: ViewGroup): T

    @Suppress("UNCHECKED_CAST")
    fun onBindBaseViewHolder(holder: BaseViewHolder) {
        this.holder = holder as T
        holder.onBindViewHolder()
    }

    abstract fun T.onBindViewHolder()
}
