package academy.kot.portal.android.recycler

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.extensions.LayoutContainer

class BaseViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    val context: Context get() = containerView.context
}
