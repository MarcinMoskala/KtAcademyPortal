package com.marcinmoskala.kotlinacademy.presentation.news

import com.marcinmoskala.kotlinacademy.common.HttpError
import com.marcinmoskala.kotlinacademy.common.launchUI
import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.presentation.BasePresenter
import com.marcinmoskala.kotlinacademy.respositories.NewsRepository
import com.marcinmoskala.kotlinacademy.usecases.PeriodicCaller

class NewsPresenter(val view: NewsView) : BasePresenter() {

    private val repository by NewsRepository.lazyGet()
    private val periodicCaller by PeriodicCaller.lazyGet()

    private var visibleNews: List<News>? = null

    override fun onCreate() {
        view.loading = true
        refreshList()
        startPeriodicRefresh()
    }

    fun onSwipeRefresh() {
        view.swipeRefresh = true
        refreshList()
    }

    private fun startPeriodicRefresh() {
        jobs += periodicCaller.start(AUTO_REFRESH_TIME_MS, callback = this::refreshList)
    }

    private fun refreshList() {
        jobs += launchUI {
            try {
                val (news) = repository.getNewsData()
                if (news != visibleNews) {
                    visibleNews = news
                    view.showList(news)
                }
            } catch (e: Throwable) {
                view.showError(e)
            } finally {
                view.swipeRefresh = false
                view.loading = false
            }
        }
    }

    companion object {
        const val AUTO_REFRESH_TIME_MS = 60_000L
    }
}