package org.kotlinacademy.common.cards

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import org.kotlinacademy.common.loadImage
import org.kotlinacademy.common.showAuthor
import org.kotlinacademy.common.startShareIntent
import org.kotlinacademy.data.*

interface InfoItemCard {
    val wholeView: View
    val titleView: TextView
    val descriptionView: TextView
    val authorView: TextView
    val imageView: ImageView?
    val shareButton: ImageView

    fun setUpInfoCard(info: Info, openUrl: (String?)->Unit) {
        val context = titleView.context
        titleView.text = info.title
        descriptionView.text = info.description
        imageView?.loadImage(info.imageUrl)
        authorView.showAuthor(info.author, info.authorUrl)
        shareButton.setOnClickListener {
            context.startShareIntent(info.title, info.getTagUrl(org.kotlinacademy.respositories.BaseURL))
        }
        wholeView.setOnClickListener {
            openUrl(info.url)
        }
    }
}