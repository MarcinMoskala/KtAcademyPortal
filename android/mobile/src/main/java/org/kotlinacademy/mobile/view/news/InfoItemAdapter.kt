package org.kotlinacademy.mobile.view.news

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.kotlinacademy.common.*
import org.kotlinacademy.common.recycler.BaseViewHolder
import org.kotlinacademy.common.recycler.ItemAdapter
import org.kotlinacademy.data.*
import org.kotlinacademy.mobile.R
import org.kotlinacademy.respositories.BaseURL

class InfoItemAdapter(
        private val info: Info
) : ItemAdapter<InfoItemAdapter.ViewHolder>(R.layout.item_info) {

    override fun onCreateViewHolder(itemView: View, parent: ViewGroup) = ViewHolder(itemView)

    override fun ViewHolder.onBindViewHolder() {
        titleView.text = info.title
        descriptionView.text = info.description
        imageView.loadImage(info.imageUrl)

        authorView.showAuthor(info.author, info.authorUrl)
        setUpListeners()
    }

    private fun ViewHolder.setUpListeners() {
        val context = itemView.context
        itemView.setOnClickListener {
            context.openUrl(info.url)
        }
        shareButton.setOnClickListener {
            context.startShareIntent(info.title, info.getTagUrl(BaseURL))
        }
    }

    class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val titleView: TextView by bindView(R.id.titleView)
        val descriptionView: TextView by bindView(R.id.descriptionView)
        val authorView: TextView by bindView(R.id.authorView)
        val imageView: ImageView by bindView(R.id.imageView)
        val shareButton: ImageView by bindView(R.id.shareButton)
    }
}