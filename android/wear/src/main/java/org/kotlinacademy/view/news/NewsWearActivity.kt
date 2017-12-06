package org.kotlinacademy.view.news

import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import com.marcinmoskala.kotlinandroidviewbindings.bindToLoading
import com.marcinmoskala.kotlinandroidviewbindings.bindToSwipeRefresh
import kotlinx.android.synthetic.main.activity_news_wear.*
import org.kotlinacademy.R
import org.kotlinacademy.common.recycler.BaseRecyclerViewAdapter
import org.kotlinacademy.data.News
import org.kotlinacademy.presentation.news.NewsPresenter
import org.kotlinacademy.presentation.news.NewsView
import org.kotlinacademy.view.WearableBaseActivity

class NewsWearActivity : WearableBaseActivity(), NewsView {

    private val presenter by presenter { NewsPresenter(this) }

    override var loading by bindToLoading(R.id.progressView, R.id.swipeRefreshView)
    override var refresh by bindToSwipeRefresh(R.id.swipeRefreshView)

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_news_wear)
        super.onCreate(savedInstanceState)

        // Enables Always-on
        setAmbientEnabled()

        newsListView.layoutManager = WearableLinearLayoutManager(this)

//        swipeRefreshView.setOnRefreshListener { presenter.onRefresh() }
//        newsListView.layoutManager = LinearLayoutManager(this)
//        fab.setOnClickListener { showGeneralCommentScreen() }
    }

    override fun showList(news: List<News>) {
        val adapters = news.map { NewsItemAdapter(it, this::onNewsClicked, this::showNewsCommentScreen, this::shareNews) }
        newsListView.adapter = BaseRecyclerViewAdapter(adapters)
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
