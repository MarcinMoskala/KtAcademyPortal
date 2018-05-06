package org.kotlinacademy.presentation.news

import org.kotlinacademy.data.News

interface OfflineNewsView {
    fun showListOffline(news: List<News>)
    fun showOfflineModeImpossible()
}