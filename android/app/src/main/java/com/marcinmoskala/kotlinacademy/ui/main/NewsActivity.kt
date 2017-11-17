package com.marcinmoskala.kotlinacademy.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.marcinmoskala.kotlinacademy.R
import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.presentation.NewsPresenter
import com.marcinmoskala.kotlinacademy.presentation.NewsView
import com.marcinmoskala.kotlinacademy.ui.common.recycler.BaseRecyclerViewAdapter
import com.marcinmoskala.kotlinacademy.ui.common.toast
import com.marcinmoskala.kotlinandroidviewbindings.bindToSwipeRefresh
import com.marcinmoskala.kotlinandroidviewbindings.bindToVisibility
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity(), NewsView {

    private val presenter by lazy { NewsPresenter(this) }

    override var loading by bindToVisibility(R.id.progressView)
    override var swipeRefresh by bindToSwipeRefresh(R.id.swipeRefreshView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        presenter.onCreate()
        swipeRefreshView.setOnRefreshListener { presenter.onSwipeRefresh() }
        newsListView.layoutManager = LinearLayoutManager(this)
    }

    override fun showList(news: List<News>) {
        val adapters = news.map { NewsItemAdapter(it, this::onNewsClicked) }
        newsListView.adapter = BaseRecyclerViewAdapter(adapters)
    }

    override fun showError(throwable: Throwable) {
        toast("Error ${throwable.message}")
    }

    private fun onNewsClicked(news: News) {
        toast("News clicked")
    }
}
