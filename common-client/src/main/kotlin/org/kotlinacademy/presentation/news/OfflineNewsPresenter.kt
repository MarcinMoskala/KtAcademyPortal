package org.kotlinacademy.presentation.news

import org.kotlinacademy.data.News
import org.kotlinacademy.respositories.NewsLocalRepository

class OfflineNewsPresenter(
        private val view: OfflineNewsView,
        private val repo: NewsLocalRepository
) {

    fun onNewsShown(news: List<News>) {
        repo.setNews(news)
    }

    fun onNoInternet() {
        val news = repo.getNews()
        if (news != null) {
            view.showList(news)
        }
    }
}