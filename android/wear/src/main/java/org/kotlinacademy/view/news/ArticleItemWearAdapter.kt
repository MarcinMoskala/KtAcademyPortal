package org.kotlinacademy.view.news

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.kotlinacademy.R
import org.kotlinacademy.common.bindView
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
) : ItemAdapter<ArticleItemWearAdapter.ViewHolder>(R.layout.item_article_wear) {

    override fun onCreateViewHolder(itemView: View, parent: ViewGroup) = ViewHolder(itemView)

    override fun ViewHolder.onBindViewHolder() {
        titleView.text = article.title
        subtitleView.text = article.subtitle
        setUpListeners()
    }

    private fun ViewHolder.setUpListeners() {
        val context = itemView.context
        itemView.setOnClickListener {
            onLinkClicked(article.url)
        }
        commentButton.setOnClickListener {
            commentClicked(article)
        }
        shareButton.setOnClickListener {
            context.startShareIntent(article.title, article.url ?: article.subtitle)
        }
    }

    class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val titleView: TextView by bindView(R.id.titleView)
        val subtitleView: TextView by bindView(R.id.subtitleView)
        val commentButton: ImageView by bindView(R.id.commentButton)
        val shareButton: ImageView by bindView(R.id.shareButton)
    }
}