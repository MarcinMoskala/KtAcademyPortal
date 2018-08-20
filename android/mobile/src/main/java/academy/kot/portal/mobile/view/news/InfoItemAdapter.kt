package academy.kot.portal.mobile.view.news

import academy.kot.portal.android.loadImage
import academy.kot.portal.android.openUrl
import academy.kot.portal.android.recycler.BaseViewHolder
import academy.kot.portal.android.recycler.ItemAdapter
import academy.kot.portal.android.showAuthor
import academy.kot.portal.android.startShareIntent
import academy.kot.portal.mobile.App
import academy.kot.portal.mobile.R
import kotlinx.android.synthetic.main.item_info.*
import org.kotlinacademy.data.*

class InfoItemAdapter(private val info: Info) : ItemAdapter(R.layout.item_info) {

    override fun BaseViewHolder.onBindViewHolder() {
        titleView.text = info.title
        descriptionView.text = info.desc
        imageView?.loadImage(info.imageUrl)
        authorView.showAuthor(info.author, info.authorUrl)
        shareButton.setOnClickListener {
            context.startShareIntent(info.title, info.getTagUrl(App.baseUrl.orEmpty()))
        }
        containerView.setOnClickListener {
            context.openUrl(info.url)
        }
    }
}