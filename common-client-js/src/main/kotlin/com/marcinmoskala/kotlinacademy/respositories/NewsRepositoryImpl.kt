package com.marcinmoskala.kotlinacademy.respositories

import com.marcinmoskala.kotlinacademy.Endpoints
import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.httpGet

class NewsRepositoryImpl : NewsRepository {
//    private val baseUrl = "http://localhost:8080/"
    private val newsUrl = Endpoints.news

    override suspend fun getNews(): List<News> = JSON.parse(httpGet(newsUrl))
}