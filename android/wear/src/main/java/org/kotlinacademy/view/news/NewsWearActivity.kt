package org.kotlinacademy.view.news

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import org.kotlinacademy.R
import org.kotlinacademy.data.News
import org.kotlinacademy.presentation.news.NewsPresenter
import org.kotlinacademy.presentation.news.NewsView
import org.kotlinacademy.view.WearableBaseActivity
import org.kotlinandroidviewbindings.bindToLoading
import org.kotlinandroidviewbindings.bindToSwipeRefresh
import kotlinx.android.synthetic.main.activity_news_wear.*

class NewsWearActivity : WearableBaseActivity(), NewsView {

    private val presenter by presenter { NewsPresenter(this) }

    override var loading by bindToLoading(R.id.progressView, R.id.swipeRefreshView)
    override var refresh by bindToSwipeRefresh(R.id.swipeRefreshView)

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_news_wear)
        super.onCreate(savedInstanceState)

        // Enables Always-on
        setAmbientEnabled()

//        swipeRefreshView.setOnRefreshListener { presenter.onRefresh() }
//        newsListView.layoutManager = LinearLayoutManager(this)
//        fab.setOnClickListener { showGeneralCommentScreen() }
    }

    override fun showList(news: List<News>) {
//        val adapters = news.map { NewsItemAdapter(it, this::onNewsClicked, this::showNewsCommentScreen, this::shareNews) }
//        newsListView.adapter = BaseRecyclerViewAdapter(adapters)
    }

    private fun showNewsCommentScreen(news: News) {
//        FeedbackActivityStarter.startForResult(this, news.id, COMMENT_CODE)
    }

    private fun showGeneralCommentScreen() {
//        FeedbackActivityStarter.startForResult(this, COMMENT_CODE)
    }

    private fun onNewsClicked(news: News) {
//        openUrl(news.url)
    }

    private fun shareNews(news: News) {
//        startShareIntent(news.title, news.url ?: news.subtitle)
    }
}
