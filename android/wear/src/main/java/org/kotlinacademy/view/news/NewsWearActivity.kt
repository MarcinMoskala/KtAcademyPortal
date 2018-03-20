package org.kotlinacademy.view.news

import activitystarter.MakeActivityStarter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import com.google.android.wearable.intent.RemoteIntent
import com.marcinmoskala.kotlinandroidviewbindings.bindToLoading
import com.marcinmoskala.kotlinandroidviewbindings.bindToSwipeRefresh
import kotlinx.android.synthetic.main.activity_news_wear.*
import org.kotlinacademy.R
import org.kotlinacademy.common.recycler.BaseRecyclerViewAdapter
import org.kotlinacademy.common.startShareIntent
import org.kotlinacademy.data.*
import org.kotlinacademy.presentation.news.NewsPresenter
import org.kotlinacademy.presentation.news.NewsView

@MakeActivityStarter
class NewsWearActivity : WearableCommentEntryActivity(), NewsView {

    private val presenter by presenter { NewsPresenter(this) }

    override var loading by bindToLoading(R.id.progressView, R.id.swipeRefreshView)
    override var refresh by bindToSwipeRefresh(R.id.swipeRefreshView)

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_news_wear)
        super.onCreate(savedInstanceState)

        // Enables Always-on
        setAmbientEnabled()

        swipeRefreshView.setOnRefreshListener { presenter.onRefresh() }
        newsListView.layoutManager = WearableLinearLayoutManager(this)
    }

    override fun showList(articles: List<Article>, infos: List<Info>, puzzlers: List<Puzzler>) {
        val adapters = articles.mapNotNull(::newsToAdapter)
        newsListView.adapter = BaseRecyclerViewAdapter(adapters)
    }

    private fun newsToAdapter(news: News) = when (news) {
        is Article -> NewsItemAdapter(news, this::onNewsClicked, this::showNewsCommentScreen, this::shareNews)
        else -> null
    }

    private fun onNewsClicked(article: Article) {
        openUrlOnPhone(article.url)
    }

    private fun shareNews(article: Article) {
        startShareIntent(article.title, article.url ?: article.subtitle)
    }

    private fun openUrlOnPhone(url: String?) {
        url ?: return
        val intent = Intent(Intent.ACTION_VIEW)
                .addCategory(Intent.CATEGORY_BROWSABLE)
                .setData(Uri.parse(url))

        RemoteIntent.startRemoteActivity(this, intent, null)
    }
}
