package com.marcinmoskala.kotlinacademy.respositories

import com.marcinmoskala.kotlinacademy.Endpoints
import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.httpGet

class NewsRepositoryImpl : NewsRepository {
    override suspend fun getNews(): List<News> = JSON.parse(httpGet(Endpoints.news))
}