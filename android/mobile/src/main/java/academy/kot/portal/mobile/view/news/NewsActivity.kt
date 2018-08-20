package academy.kot.portal.mobile.view.news

import academy.kot.portal.android.NoInternetConnectionError
import academy.kot.portal.android.PrefsCommon
import academy.kot.portal.android.di.NewsRepositoryDi
import academy.kot.portal.android.recycler.BaseRecyclerViewAdapter
import academy.kot.portal.android.toast
import academy.kot.portal.mobile.R
import academy.kot.portal.mobile.view.BaseActivity
import academy.kot.portal.mobile.view.feedback.FeedbackActivityStarter
import academy.kot.portal.mobile.view.okSnack
import activitystarter.MakeActivityStarter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.marcinmoskala.kotlinandroidviewbindings.bindToSwipeRefresh
import com.marcinmoskala.kotlinandroidviewbindings.bindToVisibility
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.coroutines.experimental.android.UI
import org.kotlinacademy.data.Article
import org.kotlinacademy.data.Info
import org.kotlinacademy.data.News
import org.kotlinacademy.data.Puzzler
import org.kotlinacademy.presentation.news.NewsPresenter
import org.kotlinacademy.presentation.news.NewsView
import org.kotlinacademy.presentation.news.OfflineNewsPresenter
import org.kotlinacademy.presentation.news.OfflineNewsView
import java.io.IOException
import java.net.ConnectException

@MakeActivityStarter
class NewsActivity : BaseActivity(), NewsView, OfflineNewsView {

    private val newsRepository by NewsRepositoryDi.lazyGet()

    private val newsPresenter by presenter { NewsPresenter(UI, this, newsRepository) }
    private val offlinePresenter by presenter { OfflineNewsPresenter(this, PrefsCommon) }

    override var loading by bindToVisibility(R.id.progressView)
    override var refresh by bindToSwipeRefresh(R.id.swipeRefreshView)

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_news)
        super.onCreate(savedInstanceState)
        swipeRefreshView.setOnRefreshListener { newsPresenter.onRefresh() }
        newsListView.layoutManager = LinearLayoutManager(this)
        fab.setOnClickListener { showGeneralCommentScreen() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode to resultCode) {
            COMMENT_CODE to Activity.RESULT_OK -> showThankYouForCommentSnack()
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun showList(news: List<News>) {
        newsListView.showNews(news)
        fab.show()
        offlinePresenter.onNewsLoaded(news)
    }

    override fun showListOffline(news: List<News>) {
        newsPresenter.cleanCache()
        newsListView.showNews(news, offline = true)
        fab.hide()
        toast(R.string.offline_mode_started)
    }

    override fun showOfflineModeImpossible() {
        fab.hide()
        showAlertDialog(R.string.offline_mode_impossible_title, R.string.offline_mode_impossible,
                action = { finish() }
        )
    }

    override fun showError(error: Throwable) {
        when (error) {
            is NoInternetConnectionError, is IOException, is ConnectException -> offlinePresenter.onNoInternet()
            else -> super.showError(error)
        }
    }

    private fun RecyclerView.showNews(news: List<News>, offline: Boolean = false) {
        val adapters = news.mapNotNull {
            when (it) {
                is Article -> ArticleItemAdapter(it, offline, ::showNewsCommentScreen)
                is Info -> InfoItemAdapter(it)
                is Puzzler -> PuzzlerItemAdapter(it)
                else -> null
            }
        }
        adapter = BaseRecyclerViewAdapter(adapters)
    }

    private fun showNewsCommentScreen(article: Article) {
        FeedbackActivityStarter.startForResult(this, article.id, COMMENT_CODE)
    }

    private fun showGeneralCommentScreen() {
        FeedbackActivityStarter.startForResult(this, COMMENT_CODE)
    }

    private fun showThankYouForCommentSnack() {
        okSnack(R.string.feedback_response)
    }

    companion object {
        const val COMMENT_CODE = 144
    }
}

