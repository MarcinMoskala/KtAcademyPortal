package com.marcinmoskala.kotlinacademy.presentation

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

    private fun refreshList(onFinish: ()->Unit) {
        repository.getNews(
                callback = view::showList,
                onError = view::showError,
                onFinish = onFinish
        )
    }
}