package org.kotlinacademy.presentation.news

import org.kotlinacademy.data.News
import org.kotlinacademy.presentation.BaseView

interface NewsView : BaseView {
    var loading: Boolean
    var refresh: Boolean
    fun showList(news: List<News>)
}