package com.marcinmoskala.kotlinacademy.ui.main

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.ui.common.bindView
import com.marcinmoskala.kotlinacademy.ui.common.loadImage
import com.marcinmoskala.kotlinacademy.ui.common.recycler.BaseViewHolder
import com.marcinmoskala.kotlinacademy.ui.common.recycler.ItemAdapter
import org.jetbrains.kotlinconf.R

class NewsItemAdapter(
        private val news: News,
        private val clicked: (News) -> Unit
) : ItemAdapter<NewsItemAdapter.ViewHolder>(R.layout.item_news) {

    override fun onCreateViewHolder(itemView: View, parent: ViewGroup) = ViewHolder(itemView)

    override fun ViewHolder.onBindViewHolder() {
        titleView.text = news.title
        subtitleView.text = news.subtitle
        imageView.loadImage(news.imageUrl)
        itemView.setOnClickListener { clicked(news) }
    }

    class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val titleView by bindView<TextView>(R.id.titleView)
        val subtitleView by bindView<TextView>(R.id.subtitleView)
        val imageView by bindView<ImageView>(R.id.imageView)
    }
}