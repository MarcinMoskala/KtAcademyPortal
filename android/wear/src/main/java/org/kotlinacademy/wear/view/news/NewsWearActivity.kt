package org.kotlinacademy.wear.view.news

import activitystarter.MakeActivityStarter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import com.google.android.wearable.intent.RemoteIntent
import com.marcinmoskala.kotlinandroidviewbindings.bindToLoading
import com.marcinmoskala.kotlinandroidviewbindings.bindToSwipeRefresh
import kotlinx.android.synthetic.main.activity_news_wear.*
import org.kotlinacademy.wear.R
import org.kotlinacademy.common.di.NewsRepositoryDi
import org.kotlinacademy.common.recycler.BaseRecyclerViewAdapter
import org.kotlinacademy.data.Article
import org.kotlinacademy.data.Info
import org.kotlinacademy.data.News
import org.kotlinacademy.data.Puzzler
import org.kotlinacademy.presentation.news.NewsPresenter
import org.kotlinacademy.presentation.news.NewsView

@MakeActivityStarter
class NewsWearActivity : WearableCommentEntryActivity(), NewsView {

    private val newsRepository by NewsRepositoryDi.lazyGet()

    private val presenter by presenter { NewsPresenter(this, newsRepository) }

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

    override fun showList(news: List<News>) {
        val adapters = news.mapNotNull(::newsToAdapter)
        newsListView.adapter = BaseRecyclerViewAdapter(adapters)
    }

    private fun newsToAdapter(news: News) = when (news) {
        is Article -> ArticleItemWearAdapter(news, this::onLinkClicked, this::showNewsCommentScreen)
        is Info -> InfoItemWearAdapter(news, this::onLinkClicked)
        is Puzzler -> PuzzlerItemWearAdapter(news)
        else -> null
    }

    private fun onLinkClicked(url: String?) {
        openUrlOnPhone(url)
    }

    private fun openUrlOnPhone(url: String?) {
        url ?: return
        val intent = Intent(Intent.ACTION_VIEW)
                .addCategory(Intent.CATEGORY_BROWSABLE)
                .setData(Uri.parse(url))

        RemoteIntent.startRemoteActivity(this, intent, null)
    }
}
