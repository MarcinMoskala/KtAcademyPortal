package academy.kot.portal.wear.view.news

import academy.kot.portal.android.recycler.BaseViewHolder
import academy.kot.portal.android.recycler.ItemAdapter
import academy.kot.portal.android.showAuthor
import academy.kot.portal.android.startShareIntent
import academy.kot.portal.wear.App
import academy.kot.portal.wear.R
import kotlinx.android.synthetic.main.item_info_wear.*
import org.kotlinacademy.data.*

class InfoItemWearAdapter(
        private val info: Info,
        private val onLinkClicked: (String?) -> Unit
) : ItemAdapter(R.layout.item_info_wear) {

    override fun BaseViewHolder.onBindViewHolder() {
        val context = titleView.context
        titleView.text = info.title
        descriptionView.text = info.desc
        authorView.showAuthor(info.author, info.authorUrl)
        shareButton.setOnClickListener {
            context.startShareIntent(info.title, info.getTagUrl(App.baseUrl ?: ""))
        }
        containerView.setOnClickListener {
            onLinkClicked(info.url)
        }
    }
}