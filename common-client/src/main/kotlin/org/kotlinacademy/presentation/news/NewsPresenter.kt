package org.kotlinacademy.presentation.news

import org.kotlinacademy.common.launchUI
import org.kotlinacademy.data.Article
import org.kotlinacademy.data.News
import org.kotlinacademy.data.NewsData
import org.kotlinacademy.presentation.BasePresenter
import org.kotlinacademy.respositories.NewsRepository

class NewsPresenter(val view: NewsView) : BasePresenter() {

    private val repository by NewsRepository.lazyGet()

    private var visibleNews: NewsData? = null

    override fun onCreate() {
        view.loading = true
        refreshList()
    }

    fun onRefresh() {
        view.refresh = true
        refreshList()
    }

    private fun refreshList() {
        jobs += launchUI {
            try {
                val newsData = repository.getNewsData()
                if (newsData == visibleNews) return@launchUI
                visibleNews = newsData

                val news = newsData.allNews().sortedByDescending { it.dateTime }
                view.showList(news, newsData)
            } catch (e: Throwable) {
                view.showError(e)
            } finally {
                view.refresh = false
                view.loading = false
            }
        }
    }

    fun NewsData.allNews(): List<News> = articles + infos + puzzlers

    companion object {
        const val AUTO_REFRESH_TIME_MS = 60_000L
    }
}