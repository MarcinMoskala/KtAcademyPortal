package org.kotlinacademy.view.news

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.kotlinacademy.R
import org.kotlinacademy.common.bindView
import org.kotlinacademy.common.cards.ArticleItemCard
import org.kotlinacademy.common.openUrl
import org.kotlinacademy.common.recycler.BaseViewHolder
import org.kotlinacademy.common.recycler.ItemAdapter
import org.kotlinacademy.data.Article

class ArticleItemWearAdapter(
        private val article: Article,
        private val onLinkClicked: (String?) -> Unit,
        private val commentClicked: (Article) -> Unit
) : ItemAdapter<ArticleItemWearAdapter.ViewHolder>(R.layout.item_article_wear) {

    override fun onCreateViewHolder(itemView: View, parent: ViewGroup) = ViewHolder(itemView)

    override fun ViewHolder.onBindViewHolder() {
        setUpArticleCard(article, openUrl = onLinkClicked, commentClicked = commentClicked)
    }

    class ViewHolder(override val wholeView: View) : BaseViewHolder(wholeView), ArticleItemCard {
        override val imageView: ImageView? = null
        override val titleView: TextView by bindView(R.id.titleView)
        override val subtitleView: TextView by bindView(R.id.subtitleView)
        override val commentButton: ImageView by bindView(R.id.commentButton)
        override val shareButton: ImageView by bindView(R.id.shareButton)
    }
}