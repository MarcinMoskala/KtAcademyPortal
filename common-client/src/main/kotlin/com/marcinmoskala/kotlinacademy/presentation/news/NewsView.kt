package com.marcinmoskala.kotlinacademy.presentation.news

import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.presentation.BaseView

interface NewsView: BaseView {
    var loading: Boolean
    var swipeRefresh: Boolean
    fun showList(news: List<News>)
}