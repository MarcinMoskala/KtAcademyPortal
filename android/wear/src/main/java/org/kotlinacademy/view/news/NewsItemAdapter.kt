package org.kotlinacademy.view.news

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.kotlinacademy.R
import org.kotlinacademy.common.bindView
import org.kotlinacademy.common.canShare
import org.kotlinacademy.common.recycler.BaseViewHolder
import org.kotlinacademy.common.recycler.ItemAdapter
import org.kotlinacademy.common.visible
import org.kotlinacademy.data.Article
import org.kotlinacademy.data.*
class NewsItemAdapter(
        private val article: Article,
        private val clicked: (Article) -> Unit,
        private val commentClicked: (Article)->Unit,
        private val shareClicked: (Article)->Unit
) : ItemAdapter<NewsItemAdapter.ViewHolder>(R.layout.item_news_wear) {

    override fun onCreateViewHolder(itemView: View, parent: ViewGroup) = ViewHolder(itemView)

    override fun ViewHolder.onBindViewHolder() {
        val context = itemView.context
        titleView.text = article.title
        subtitleView.text = article.subtitle
        itemView.setOnClickListener { clicked(article) }
        commentButton.setOnClickListener { commentClicked(article) }
        shareButton.setOnClickListener { shareClicked(article) }
        shareButton.visible = context.canShare()
    }

    class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val titleView: TextView by bindView(R.id.titleView)
        val subtitleView: TextView by bindView(R.id.subtitleView)
        val commentButton: ImageView by bindView(R.id.commentButton)
        val shareButton: ImageView by bindView(R.id.shareButton)
    }
}