package org.kotlinacademy.presentation.news

import org.kotlinacademy.data.News
import org.kotlinacademy.data.NewsData
import org.kotlinacademy.presentation.BaseView

interface NewsView : BaseView {
    var loading: Boolean
    var refresh: Boolean

    // We pass newsData because of typing problems in Kotlin/JS
    fun showList(news: List<News>, newsData: NewsData)
}