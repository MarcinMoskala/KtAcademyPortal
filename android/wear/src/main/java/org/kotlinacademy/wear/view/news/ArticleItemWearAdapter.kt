package org.kotlinacademy.wear.view.news

import kotlinx.android.synthetic.main.item_article_wear.*
import org.kotlinacademy.wear.R
import org.kotlinacademy.common.recycler.BaseViewHolder
import org.kotlinacademy.common.recycler.ItemAdapter
import org.kotlinacademy.common.startShareIntent
import org.kotlinacademy.data.Article
import org.kotlinacademy.data.subtitle
import org.kotlinacademy.data.title
import org.kotlinacademy.data.url

class ArticleItemWearAdapter(
        private val article: Article,
        private val onLinkClicked: (String?) -> Unit,
        private val commentClicked: (Article) -> Unit
) : ItemAdapter(R.layout.item_article_wear) {

    override fun BaseViewHolder.onBindViewHolder() {
        val context = titleView.context
        titleView.text = article.title
        subtitleView.text = article.subtitle
        containerView.setOnClickListener {
            onLinkClicked(article.url)
        }
        commentButton.setOnClickListener {
            commentClicked(article)
        }
        shareButton.setOnClickListener {
            context.startShareIntent(article.title, article.url ?: article.subtitle)
        }
    }
}