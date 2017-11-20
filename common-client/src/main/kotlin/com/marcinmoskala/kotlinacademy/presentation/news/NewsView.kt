package com.marcinmoskala.kotlinacademy.presentation.news

import com.marcinmoskala.kotlinacademy.data.News

interface NewsView {
    var loading: Boolean
    var swipeRefresh: Boolean
    fun showList(news: List<News>)
    fun showError(throwable: Throwable)
}