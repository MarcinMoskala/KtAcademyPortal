package com.marcinmoskala.kotlinacademy.ui.view.news

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.marcinmoskala.kotlinacademy.R
import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.ui.common.bindView
import com.marcinmoskala.kotlinacademy.ui.common.loadImage
import com.marcinmoskala.kotlinacademy.ui.common.recycler.BaseViewHolder
import com.marcinmoskala.kotlinacademy.ui.common.recycler.ItemAdapter

class NewsItemAdapter(
        private val news: News,
        private val clicked: (News) -> Unit,
        private val commentClicked: (News)->Unit,
        private val shareClicked: (News)->Unit
) : ItemAdapter<NewsItemAdapter.ViewHolder>(R.layout.item_news) {

    override fun onCreateViewHolder(itemView: View, parent: ViewGroup) = ViewHolder(itemView)

    override fun ViewHolder.onBindViewHolder() {
        titleView.text = news.title
        subtitleView.text = news.subtitle
        imageView.loadImage(news.imageUrl)
        itemView.setOnClickListener { clicked(news) }
        commentButton.setOnClickListener { commentClicked(news) }
        shareButton.setOnClickListener { shareClicked(news) }
    }

    class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val titleView: TextView by bindView(R.id.titleView)
        val subtitleView: TextView by bindView(R.id.subtitleView)
        val imageView: ImageView by bindView(R.id.imageView)
        val commentButton: ImageView by bindView(R.id.commentButton)
        val shareButton: ImageView by bindView(R.id.shareButton)
    }
}