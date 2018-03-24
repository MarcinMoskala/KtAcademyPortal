package org.kotlinacademy.view.news

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.kotlinacademy.R
import org.kotlinacademy.common.bindView
import org.kotlinacademy.common.openUrl
import org.kotlinacademy.common.recycler.BaseViewHolder
import org.kotlinacademy.common.recycler.ItemAdapter
import org.kotlinacademy.common.showAuthor
import org.kotlinacademy.common.startShareIntent
import org.kotlinacademy.data.*

class InfoItemWearAdapter(
        private val info: Info,
        private val onLinkClicked: (String?) -> Unit
) : ItemAdapter<InfoItemWearAdapter.ViewHolder>(R.layout.item_info_wear) {

    override fun onCreateViewHolder(itemView: View, parent: ViewGroup) = ViewHolder(itemView)

    override fun ViewHolder.onBindViewHolder() {
        titleView.text = info.title
        descriptionView.text = info.description
        authorView.showAuthor(info.author, info.authorUrl, onClick = onLinkClicked)
        setUpListeners()
    }

    private fun ViewHolder.setUpListeners() {
        val context = itemView.context
        itemView.setOnClickListener {
            onLinkClicked(info.url)
        }
        shareButton.setOnClickListener {
            context.startShareIntent(info.title, info.tagUrl)
        }
    }

    class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val titleView: TextView by bindView(R.id.titleView)
        val descriptionView: TextView by bindView(R.id.descriptionView)
        val authorView: TextView by bindView(R.id.authorView)
        val shareButton: ImageView by bindView(R.id.shareButton)
    }
}