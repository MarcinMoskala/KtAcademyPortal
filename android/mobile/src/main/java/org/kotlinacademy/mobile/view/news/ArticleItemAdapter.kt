package org.kotlinacademy.mobile.view.news

import kotlinx.android.synthetic.main.item_article.*
import org.kotlinacademy.common.loadImage
import org.kotlinacademy.common.openUrl
import org.kotlinacademy.common.recycler.BaseViewHolder
import org.kotlinacademy.common.recycler.ItemAdapter
import org.kotlinacademy.common.startShareIntent
import org.kotlinacademy.data.*
import org.kotlinacademy.mobile.R

class ArticleItemAdapter(
        private val article: Article,
        private val commentClicked: (Article) -> Unit
) : ItemAdapter(R.layout.item_article) {

    override fun BaseViewHolder.onBindViewHolder() {
        titleView.text = article.title
        subtitleView.text = article.subtitle
        imageView?.loadImage(article.imageUrl)
        containerView.setOnClickListener {
            context.openUrl(article.url)
        }
        commentButton.setOnClickListener {
            commentClicked(article)
        }
        shareButton.setOnClickListener {
            context.startShareIntent(article.title, article.url ?: article.subtitle)
        }
    }
}