package academy.kot.portal.android.recycler

import android.support.annotation.LayoutRes
import android.view.View
import android.view.ViewGroup

abstract class ItemAdapter(@LayoutRes open val layoutId: Int) {

    protected var holder: BaseViewHolder? = null

    fun onCreateViewHolder(itemView: View, parent: ViewGroup) = BaseViewHolder(itemView)

    @Suppress("UNCHECKED_CAST")
    fun onBindBaseViewHolder(holder: BaseViewHolder) {
        holder.onBindViewHolder()
        this.holder = holder
    }

    abstract fun BaseViewHolder.onBindViewHolder()
}
