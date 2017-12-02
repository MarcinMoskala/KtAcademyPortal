package com.marcinmoskala.kotlinacademy.respositories

import com.marcinmoskala.kotlinacademy.Endpoints
import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.data.NewsData
import com.marcinmoskala.kotlinacademy.httpGet
import kotlinx.serialization.json.JSON

class NewsRepositoryImpl(val json: JSON) : NewsRepository {
    override suspend fun getNewsData(): NewsData = json.parse(httpGet(Endpoints.news))
}