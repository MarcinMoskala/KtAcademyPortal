package org.kotlinacademy.view.news

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.kotlinacademy.R
import org.kotlinacademy.common.bindView
import org.kotlinacademy.common.recycler.BaseViewHolder
import org.kotlinacademy.common.recycler.ItemAdapter
import org.kotlinacademy.common.cards.InfoItemCard
import org.kotlinacademy.data.*

class InfoItemWearAdapter(
        private val info: Info,
        private val onLinkClicked: (String?) -> Unit
) : ItemAdapter<InfoItemWearAdapter.ViewHolder>(R.layout.item_info_wear) {

    override fun onCreateViewHolder(itemView: View, parent: ViewGroup) = ViewHolder(itemView)

    override fun ViewHolder.onBindViewHolder() {
        setUpInfoCard(info, openUrl = onLinkClicked)
    }

    class ViewHolder(override val wholeView: View) : BaseViewHolder(wholeView), InfoItemCard {
        override val imageView: ImageView? = null
        override val titleView: TextView by bindView(R.id.titleView)
        override val descriptionView: TextView by bindView(R.id.descriptionView)
        override val authorView: TextView by bindView(R.id.authorView)
        override val shareButton: ImageView by bindView(R.id.shareButton)
    }
}