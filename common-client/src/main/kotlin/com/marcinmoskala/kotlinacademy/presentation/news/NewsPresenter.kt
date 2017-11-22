package com.marcinmoskala.kotlinacademy.presentation.news

import com.marcinmoskala.kotlinacademy.common.HttpError
import com.marcinmoskala.kotlinacademy.common.delay
import com.marcinmoskala.kotlinacademy.common.launchUI
import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.presentation.BasePresenter
import com.marcinmoskala.kotlinacademy.respositories.NewsRepository

class NewsPresenter(val view: NewsView) : BasePresenter() {

    private val repository by NewsRepository.lazyGet()

    private var visibleNews: List<News>? = null

    override fun onCreate() {
        view.loading = true
        refreshList(onFinish = { view.loading = false })
        startPeriodicRefresh()
    }

    private fun startPeriodicRefresh() {
        jobs += launchUI {
            while (true) {
                delay(AUTO_REFRESH_TIME_MS)
                refreshList()
            }
        }
    }

    fun onSwipeRefresh() {
        refreshList(onFinish = { view.swipeRefresh = false })
    }

    private fun refreshList(onFinish: () -> Unit = {}) {
        jobs += launchUI {
            try {
                val (news) = repository.getNewsData()
                if (news != visibleNews) {
                    visibleNews = news
                    view.showList(news)
                }
            } catch (e: HttpError) {
                view.showError(Error("Http ${e.code} error: ${e.message}"))
            } catch (e: Throwable) {
                view.showError(e)
            } finally {
                onFinish()
            }
        }
    }

    companion object {
        const val AUTO_REFRESH_TIME_MS = 60_000L
    }
}