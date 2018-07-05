package org.kotlinacademy.respositories

import org.kotlinacademy.data.News

interface NewsLocalRepository {

    fun getNews(): List<News>?
    fun setNews(news: List<News>)
}