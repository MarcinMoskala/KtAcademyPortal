package com.marcinmoskala.kotlinacademy.ui.view.news

import activitystarter.MakeActivityStarter
import android.app.Activity
import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.marcinmoskala.kotlinacademy.R
import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.presentation.news.NewsPresenter
import com.marcinmoskala.kotlinacademy.presentation.news.NewsView
import com.marcinmoskala.kotlinacademy.ui.common.openUrl
import com.marcinmoskala.kotlinacademy.ui.common.recycler.BaseRecyclerViewAdapter
import com.marcinmoskala.kotlinacademy.ui.common.startShareIntent
import com.marcinmoskala.kotlinacademy.ui.view.BaseActivity
import com.marcinmoskala.kotlinacademy.ui.view.feedback.FeedbackActivityStarter
import com.marcinmoskala.kotlinacademy.ui.view.okSnack
import com.marcinmoskala.kotlinandroidviewbindings.bindToSwipeRefresh
import com.marcinmoskala.kotlinandroidviewbindings.bindToVisibility
import kotlinx.android.synthetic.main.activity_news.*

@MakeActivityStarter
class NewsActivity : BaseActivity(), NewsView {

    private val presenter by presenter { NewsPresenter(this) }

    override var loading by bindToVisibility(R.id.progressView)
    override var swipeRefresh by bindToSwipeRefresh(R.id.swipeRefreshView)

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_news)
        super.onCreate(savedInstanceState)
        swipeRefreshView.setOnRefreshListener { presenter.onSwipeRefresh() }
        newsListView.layoutManager = LinearLayoutManager(this)
        fab.setOnClickListener { showGeneralCommentScreen() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when {
            requestCode == COMMENT_CODE && resultCode == Activity.RESULT_OK -> showThankYouForCommentSnack()
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
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

    companion object {
        val COMMENT_CODE = 144
    }
}