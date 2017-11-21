package com.marcinmoskala.kotlinacademy.respositories

import com.marcinmoskala.kotlinacademy.Endpoints
import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.data.NewsData
import com.marcinmoskala.kotlinacademy.httpGet
import kotlinx.serialization.json.JSON

class NewsRepositoryImpl : NewsRepository {
//    private val baseUrl = "http://localhost:8080/"
    private val newsUrl = Endpoints.news

    override suspend fun getNewsData(): NewsData = JSON.parse(httpGet(newsUrl))
}