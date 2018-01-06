package org.kotlinacademy.presentation.news

import org.kotlinacademy.common.launchUI
import org.kotlinacademy.data.News
import org.kotlinacademy.presentation.BasePresenter
import org.kotlinacademy.respositories.NewsRepository
import org.kotlinacademy.usecases.PeriodicCaller

class NewsPresenter(val view: NewsView) : BasePresenter() {

    private val repository by NewsRepository.lazyGet()
    private val periodicCaller by PeriodicCaller.lazyGet()

    private var visibleNews: List<News>? = null

    override fun onCreate() {
        view.loading = true
        refreshList()
        startPeriodicRefresh()
    }

    fun onRefresh() {
        view.refresh = true
        refreshList()
    }

    private fun startPeriodicRefresh() {
        jobs += periodicCaller.start(AUTO_REFRESH_TIME_MS, callback = this::refreshList)
    }

    private fun refreshList() {
        jobs += launchUI {
            try {
                val news = repository.getNewsData()
                        .news
                        .sortedByDescending { it.occurrence }
                if (news == visibleNews) return@launchUI
                visibleNews = news
                view.showList(news)
            } catch (e: Throwable) {
                view.showError(e)
            } finally {
                view.refresh = false
                view.loading = false
            }
        }
    }

    companion object {
        const val AUTO_REFRESH_TIME_MS = 60_000L
    }
}