package com.marcinmoskala.kotlinacademy.presentation

import com.marcinmoskala.kotlinacademy.common.HttpError
import com.marcinmoskala.kotlinacademy.common.launchUI
import com.marcinmoskala.kotlinacademy.respositories.NewsRepository

class NewsPresenter(val view: NewsView) {

    private val repository by NewsRepository.lazyGet()

    fun onCreate() {
        view.loading = true
        refreshList(onFinish = { view.loading = false })
    }

    fun onSwipeRefresh() {
        refreshList(onFinish = { view.swipeRefresh = false })
    }

    private fun refreshList(onFinish: () -> Unit) {
        launchUI {
            try {
                val news = repository.getNews()
                view.showList(news)
            } catch (e: HttpError) {
                view.showError(Error("Http ${e.code} error: ${e.message}"))
            } catch (e: Throwable) {
                view.showError(e)
            } finally {
                onFinish()
            }
        }
    }
}