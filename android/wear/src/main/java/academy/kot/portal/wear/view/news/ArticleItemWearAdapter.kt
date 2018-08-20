package academy.kot.portal.wear.view.news

import academy.kot.portal.android.recycler.BaseViewHolder
import academy.kot.portal.android.recycler.ItemAdapter
import academy.kot.portal.android.startShareIntent
import academy.kot.portal.wear.R
import kotlinx.android.synthetic.main.item_article_wear.*
import org.kotlinacademy.data.Article
import org.kotlinacademy.data.subtitle
import org.kotlinacademy.data.title
import org.kotlinacademy.data.url

class ArticleItemWearAdapter(
        private val article: Article,
        private val onLinkClicked: (String?) -> Unit,
        private val commentClicked: (Article) -> Unit
) : ItemAdapter(R.layout.item_article_wear) {

    override fun BaseViewHolder.onBindViewHolder() {
        val context = titleView.context
        titleView.text = article.title
        subtitleView.text = article.subtitle
        containerView.setOnClickListener {
            onLinkClicked(article.url)
        }
        commentButton.setOnClickListener {
            commentClicked(article)
        }
        shareButton.setOnClickListener {
            context.startShareIntent(article.title, article.url ?: article.subtitle)
        }
    }
}