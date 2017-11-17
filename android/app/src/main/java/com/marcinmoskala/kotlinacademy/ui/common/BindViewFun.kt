@file:Suppress("UNCHECKED_CAST")
package com.marcinmoskala.kotlinacademy.ui.common

import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View

fun  <T: View> ViewHolder.bindView(viewId: Int) = lazy { itemView.findViewById<T>(viewId) }

@Suppress("UNCHECKED_CAST")
fun <T : View> View.bindView(viewId: Int) = lazy { findViewById<T>(viewId) }