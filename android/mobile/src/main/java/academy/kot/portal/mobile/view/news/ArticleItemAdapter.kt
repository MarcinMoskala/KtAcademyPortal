package academy.kot.portal.mobile.view.news

import academy.kot.portal.android.hide
import academy.kot.portal.android.loadImage
import academy.kot.portal.android.openUrl
import academy.kot.portal.android.recycler.BaseViewHolder
import academy.kot.portal.android.recycler.ItemAdapter
import academy.kot.portal.android.startShareIntent
import academy.kot.portal.mobile.R
import kotlinx.android.synthetic.main.item_article.*
import org.kotlinacademy.data.*

class ArticleItemAdapter(
        private val article: Article,
        private val offline: Boolean,
        private val commentClicked: (Article) -> Unit
) : ItemAdapter(R.layout.item_article) {

    override fun BaseViewHolder.onBindViewHolder() {
        titleView.text = article.title
        subtitleView.text = article.subtitle
        imageView?.loadImage(article.imageUrl)
        containerView.setOnClickListener {
            context.openUrl(article.url)
        }
        shareButton.setOnClickListener {
            context.startShareIntent(article.title, article.url ?: article.subtitle)
        }

        if (offline) {
            commentButton.hide()
        } else {
            commentButton.setOnClickListener {
                commentClicked(article)
            }
        }
    }
}