package org.kotlinacademy.presentation.news

import org.kotlinacademy.common.launchUI
import org.kotlinacademy.data.NewsData
import org.kotlinacademy.data.news
import org.kotlinacademy.presentation.BasePresenter
import org.kotlinacademy.respositories.NewsRepository

class NewsPresenter(
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
        jobs += launchUI {
            try {
                val newsData = newsRepository.getNewsData()
                if (newsData == visibleNews) return@launchUI
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