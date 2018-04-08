package org.kotlinacademy.mobile.view.news

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.kotlinacademy.common.bindView
import org.kotlinacademy.common.cards.ArticleItemCard
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
) : ItemAdapter<ArticleItemAdapter.ViewHolder>(R.layout.item_article) {

    override fun onCreateViewHolder(itemView: View, parent: ViewGroup) = ViewHolder(itemView)

    override fun ViewHolder.onBindViewHolder() {
        setUpArticleCard(article, openUrl = titleView.context::openUrl, commentClicked = commentClicked)
    }

    class ViewHolder(override val wholeView: View) : BaseViewHolder(wholeView), ArticleItemCard {
        override val titleView: TextView by bindView(R.id.titleView)
        override val subtitleView: TextView by bindView(R.id.subtitleView)
        override val imageView: ImageView by bindView(R.id.imageView)
        override val commentButton: ImageView by bindView(R.id.commentButton)
        override val shareButton: ImageView by bindView(R.id.shareButton)
    }
}