package org.kotlinacademy.mobile.view.news

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.kotlinacademy.common.bindView
import org.kotlinacademy.common.cards.InfoItemCard
import org.kotlinacademy.common.openUrl
import org.kotlinacademy.common.recycler.BaseViewHolder
import org.kotlinacademy.common.recycler.ItemAdapter
import org.kotlinacademy.data.Info
import org.kotlinacademy.mobile.App
import org.kotlinacademy.mobile.R

class InfoItemAdapter(
        private val info: Info
) : ItemAdapter<InfoItemAdapter.ViewHolder>(R.layout.item_info) {

    override fun onCreateViewHolder(itemView: View, parent: ViewGroup) = ViewHolder(itemView)

    override fun ViewHolder.onBindViewHolder() {
        setUpInfoCard(info, openUrl = wholeView.context::openUrl, baseUrl = App.baseUrl.orEmpty())
    }

    class ViewHolder(override val wholeView: View) : BaseViewHolder(wholeView), InfoItemCard {
        override val titleView: TextView by bindView(R.id.titleView)
        override val descriptionView: TextView by bindView(R.id.descriptionView)
        override val authorView: TextView by bindView(R.id.authorView)
        override val imageView: ImageView by bindView(R.id.imageView)
        override val shareButton: ImageView by bindView(R.id.shareButton)
    }
}