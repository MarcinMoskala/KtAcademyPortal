package org.kotlinacademy.wear.view.news

import kotlinx.android.synthetic.main.item_info_wear.*
import org.kotlinacademy.wear.R
import org.kotlinacademy.common.recycler.BaseViewHolder
import org.kotlinacademy.common.recycler.ItemAdapter
import org.kotlinacademy.common.showAuthor
import org.kotlinacademy.common.startShareIntent
import org.kotlinacademy.data.*
import org.kotlinacademy.wear.App

class InfoItemWearAdapter(
        private val info: Info,
        private val onLinkClicked: (String?) -> Unit
) : ItemAdapter(R.layout.item_info_wear) {

    override fun BaseViewHolder.onBindViewHolder() {
        val context = titleView.context
        titleView.text = info.title
        descriptionView.text = info.description
        authorView.showAuthor(info.author, info.authorUrl)
        shareButton.setOnClickListener {
            context.startShareIntent(info.title, info.getTagUrl(App.baseUrl ?: ""))
        }
        containerView.setOnClickListener {
            onLinkClicked(info.url)
        }
    }
}