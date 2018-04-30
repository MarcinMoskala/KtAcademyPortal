package org.kotlinacademy.presentation.news

import org.kotlinacademy.data.News

interface OfflineNewsView {
    var fabVisible: Boolean
    fun showList(news: List<News>)
}