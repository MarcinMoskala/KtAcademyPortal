package com.marcinmoskala.kotlinacademy.ui.view.news

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.marcinmoskala.kotlinacademy.R
import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.presentation.news.NewsPresenter
import com.marcinmoskala.kotlinacademy.presentation.news.NewsView
import com.marcinmoskala.kotlinacademy.ui.common.nullIfBlank
import com.marcinmoskala.kotlinacademy.ui.common.recycler.BaseRecyclerViewAdapter
import com.marcinmoskala.kotlinacademy.ui.view.BaseActivity
import com.marcinmoskala.kotlinacademy.ui.view.comment.CommentActivityStarter
import com.marcinmoskala.kotlinacademy.ui.view.okSnack
import com.marcinmoskala.kotlinandroidviewbindings.bindToSwipeRefresh
import com.marcinmoskala.kotlinandroidviewbindings.bindToVisibility
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : BaseActivity(), NewsView {

    private val presenter by presenter { NewsPresenter(this) }

    override var loading by bindToVisibility(R.id.progressView)
    override var swipeRefresh by bindToSwipeRefresh(R.id.swipeRefreshView)

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_news)
        super.onCreate(savedInstanceState)
        swipeRefreshView.setOnRefreshListener { presenter.onSwipeRefresh() }
        newsListView.layoutManager = LinearLayoutManager(this)
        fab.setOnClickListener { showCommentScreen() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when {
            requestCode == COMMENT_CODE && resultCode == Activity.RESULT_OK -> showThankYouForCommentSnack()
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun showList(news: List<News>) {
        val adapters = news.map { NewsItemAdapter(it, this::onNewsClicked, this::showCommentScreen) }
        newsListView.adapter = BaseRecyclerViewAdapter(adapters)
    }

    private fun showCommentScreen(news: News? = null) {
        CommentActivityStarter.startForResult(this, news?.id, COMMENT_CODE)
    }

    private fun onNewsClicked(news: News) {
        val url = news.url.nullIfBlank() ?: return
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(browserIntent)
        }
    }

    private fun showThankYouForCommentSnack() {
        okSnack("Thank you for your comment")
    }

    companion object {
        val COMMENT_CODE = 144
    }
}