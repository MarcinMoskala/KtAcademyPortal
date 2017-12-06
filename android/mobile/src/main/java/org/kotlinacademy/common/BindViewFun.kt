@file:Suppress("UNCHECKED_CAST")
package org.kotlinacademy.common

import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View

fun  <T: View> ViewHolder.bindView(viewId: Int) = lazy { itemView.findViewById<T>(viewId) }