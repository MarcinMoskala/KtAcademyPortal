package org.kotlinacademy.common.cards

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import org.kotlinacademy.common.*
import org.kotlinacademy.data.*

interface ArticleItemCard {
    val wholeView: View
    val titleView: TextView
    val subtitleView: TextView
    val imageView: ImageView?
    val commentButton: ImageView
    val shareButton: ImageView

    fun setUpArticleCard(article: Article, openUrl: (String?)->Unit, commentClicked: (Article)->Unit) {
        val context = titleView.context
        titleView.text = article.title
        subtitleView.text = article.subtitle
        imageView?.loadImage(article.imageUrl)
        wholeView.setOnClickListener {
            openUrl(article.url)
        }
        commentButton.setOnClickListener {
            commentClicked(article)
        }
        shareButton.setOnClickListener {
            context.startShareIntent(article.title, article.url ?: article.subtitle)
        }
    }
}