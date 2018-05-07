package org.kotlinacademy.presentation.news

import org.kotlinacademy.data.News
import org.kotlinacademy.presentation.BasePresenter
import org.kotlinacademy.respositories.NewsLocalRepository

class OfflineNewsPresenter(
        private val view: OfflineNewsView,
        private val repo: NewsLocalRepository
) : BasePresenter() {

    fun onNewsLoaded(news: List<News>) {
        repo.setNews(news)
    }

    fun onNoInternet() {
        try {
            val news = repo.getNews()
            if (news != null) {
                view.showListOffline(news)
            } else {
                view.showOfflineModeImpossible()
            }
        } catch (e: Throwable) {
            view.logError(e)
        }
    }
}