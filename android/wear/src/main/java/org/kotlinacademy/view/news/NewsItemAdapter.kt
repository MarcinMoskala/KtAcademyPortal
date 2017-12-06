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
import org.kotlinacademy.data.News

class NewsItemAdapter(
        private val news: News,
        private val clicked: (News) -> Unit,
        private val commentClicked: (News)->Unit,
        private val shareClicked: (News)->Unit
) : ItemAdapter<NewsItemAdapter.ViewHolder>(R.layout.item_news_wear) {

    override fun onCreateViewHolder(itemView: View, parent: ViewGroup) = ViewHolder(itemView)

    override fun ViewHolder.onBindViewHolder() {
        val context = itemView.context
        titleView.text = news.title
        subtitleView.text = news.subtitle
        itemView.setOnClickListener { clicked(news) }
        commentButton.setOnClickListener { commentClicked(news) }
        shareButton.setOnClickListener { shareClicked(news) }
        shareButton.visible = context.canShare()
    }

    class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val titleView: TextView by bindView(R.id.titleView)
        val subtitleView: TextView by bindView(R.id.subtitleView)
        val commentButton: ImageView by bindView(R.id.commentButton)
        val shareButton: ImageView by bindView(R.id.shareButton)
    }
}