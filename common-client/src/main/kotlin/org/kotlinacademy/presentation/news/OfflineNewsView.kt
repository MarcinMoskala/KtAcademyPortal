package org.kotlinacademy.presentation.news

import org.kotlinacademy.data.News
import org.kotlinacademy.presentation.BaseView

interface OfflineNewsView: BaseView {
    fun showListOffline(news: List<News>)
    fun showOfflineModeImpossible()
}