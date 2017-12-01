package com.marcinmoskala.kotlinacademy.view.news

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.marcinmoskala.kotlinacademy.R
import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.presentation.news.NewsPresenter
import com.marcinmoskala.kotlinacademy.presentation.news.NewsView
import com.marcinmoskala.kotlinacademy.view.WearableBaseActivity
import com.marcinmoskala.kotlinandroidviewbindings.bindToSwipeRefresh
import kotlinx.android.synthetic.main.activity_news_wear.*

class NewsWearActivity : WearableBaseActivity(), NewsView {

    private val presenter by presenter { NewsPresenter(this) }

    override var loading = false //by bindToVisibility(R.id.progressView)
    override var refresh by bindToSwipeRefresh(R.id.swipeRefreshView)

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_news_wear)
        super.onCreate(savedInstanceState)

        // Enables Always-on
        setAmbientEnabled()

//        swipeRefreshView.setOnRefreshListener { presenter.onRefresh() }
        newsListView.layoutManager = LinearLayoutManager(this)
//        fab.setOnClickListener { showGeneralCommentScreen() }
    }

    override fun showList(news: List<News>) {
        val adapters = news.map { NewsItemAdapter(it, this::onNewsClicked, this::showNewsCommentScreen, this::shareNews) }
        newsListView.adapter = BaseRecyclerViewAdapter(adapters)
    }

    private fun showNewsCommentScreen(news: News) {
        FeedbackActivityStarter.startForResult(this, news.id, COMMENT_CODE)
    }

    private fun showGeneralCommentScreen() {
        FeedbackActivityStarter.startForResult(this, COMMENT_CODE)
    }

    private fun onNewsClicked(news: News) {
        openUrl(news.url)
    }

    private fun shareNews(news: News) {
        startShareIntent(news.title, news.url ?: news.subtitle)
    }

    private fun showThankYouForCommentSnack() {
        okSnack(R.string.feedback_response)
    }

}
