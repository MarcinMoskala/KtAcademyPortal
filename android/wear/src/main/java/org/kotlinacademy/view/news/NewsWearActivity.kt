package org.kotlinacademy.view.news

import android.app.Activity
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
import org.kotlinacademy.common.toast
import org.kotlinacademy.data.News
import org.kotlinacademy.presentation.news.NewsPresenter
import org.kotlinacademy.presentation.news.NewsView
import org.kotlinacademy.view.WearableBaseActivity
import org.kotlinacademy.view.feedback.FeedbackActivityStarter


class NewsWearActivity : WearableBaseActivity(), NewsView {

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
//        fab.setOnClickListener { showGeneralCommentScreen() }
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

    private fun showThankYouForCommentSnack() {
        toast(getString(R.string.feedback_response))
    }

    private fun onNewsClicked(news: News) {
        openUrlOnPhone(news.url)
    }

    private fun shareNews(news: News) {
        startShareIntent(news.title, news.url ?: news.subtitle)
    }

    private fun openUrlOnPhone(url: String?) {
        url ?: return
        val intent = Intent(Intent.ACTION_VIEW)
                .addCategory(Intent.CATEGORY_BROWSABLE)
                .setData(Uri.parse(url))

        RemoteIntent.startRemoteActivity(this, intent, null)
    }

    companion object {
        val COMMENT_CODE = 144
    }
}
