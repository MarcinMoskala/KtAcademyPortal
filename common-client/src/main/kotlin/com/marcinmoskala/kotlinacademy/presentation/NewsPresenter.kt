package com.marcinmoskala.kotlinacademy.presentation

import com.marcinmoskala.kotlinacademy.respositories.NewsRepository

class NewsPresenter(val view: NewsView) {

    private val repository by NewsRepository.lazyGet()

    fun onCreate() {
        view.loading = true
        repository.getNews(
                callback = view::showList,
                onError = view::showError,
                onFinish = { view.loading = false }
        )
    }
}