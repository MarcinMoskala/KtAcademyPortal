package org.kotlinacademy.presentation.news

import kotlinx.coroutines.experimental.launch
import org.kotlinacademy.data.NewsData
import org.kotlinacademy.data.news
import org.kotlinacademy.presentation.BasePresenter
import org.kotlinacademy.respositories.NewsRepository
import kotlin.coroutines.experimental.CoroutineContext

class NewsPresenter(
        private val uiContext: CoroutineContext,
        private val view: NewsView,
        private val newsRepository: NewsRepository
) : BasePresenter() {

    private var visibleNews: NewsData? = null

    override fun onCreate() {
        view.loading = true
        refreshList()
    }

    fun onRefresh() {
        view.refresh = true
        refreshList()
    }

    fun cleanCache() {
        visibleNews = null
    }

    private fun refreshList() {
        jobs += launch(uiContext) {
            try {
                val newsData = newsRepository.getNewsData()
                if (newsData == visibleNews) return@launch
                visibleNews = newsData

                val news = newsData.news()
                        .sortedByDescending { it.dateTime }

                view.showList(news)
            } catch (e: Throwable) {
                view.showError(e)
            } finally {
                view.refresh = false
                view.loading = false
            }
        }
    }
}