package org.kotlinacademy.presentation.news

import kotlinx.coroutines.experimental.launch
import org.kotlinacademy.data.News
import org.kotlinacademy.data.NewsData
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

                val news = sortAlternately(newsData.articles, newsData.infos, newsData.puzzlers, newsData.snippets)
                view.showList(news)
            } catch (e: Throwable) {
                view.showError(e)
            } finally {
                view.refresh = false
                view.loading = false
            }
        }
    }

    private fun sortAlternately(vararg newsSources: List<News>): List<News> {
        val newsSourcesSorted = newsSources
                .map { source -> source.sortedByDescending { it.dateTime } }
                .sortedByDescending { it.firstOrNull()?.dateTime }

        val maxSize = newsSourcesSorted.map { it.size }.max()!!
        return (0 until maxSize).flatMap { i -> newsSourcesSorted.mapNotNull { it.getOrNull(i) } }
    }
}