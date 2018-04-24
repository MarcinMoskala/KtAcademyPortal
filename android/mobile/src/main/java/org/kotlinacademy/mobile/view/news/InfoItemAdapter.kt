package org.kotlinacademy.mobile.view.news

import kotlinx.android.synthetic.main.item_info.*
import org.kotlinacademy.common.loadImage
import org.kotlinacademy.common.openUrl
import org.kotlinacademy.common.recycler.BaseViewHolder
import org.kotlinacademy.common.recycler.ItemAdapter
import org.kotlinacademy.common.showAuthor
import org.kotlinacademy.common.startShareIntent
import org.kotlinacademy.data.*
import org.kotlinacademy.mobile.App
import org.kotlinacademy.mobile.R

class InfoItemAdapter(private val info: Info) : ItemAdapter(R.layout.item_info) {

    override fun BaseViewHolder.onBindViewHolder() {
        titleView.text = info.title
        descriptionView.text = info.description
        imageView?.loadImage(info.imageUrl)
        authorView.showAuthor(info.author, info.authorUrl)
        shareButton.setOnClickListener {
            context.startShareIntent(info.title, info.getTagUrl(App.baseUrl.orEmpty()))
        }
        containerView.setOnClickListener {
            context.openUrl(info.url)
        }
    }
}