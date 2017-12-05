package org.kotlinacademy.respositories

import org.kotlinacademy.Endpoints
import org.kotlinacademy.data.News
import org.kotlinacademy.data.NewsData
import org.kotlinacademy.httpGet
import kotlinx.serialization.json.JSON

class NewsRepositoryImpl(val json: JSON) : NewsRepository {
    override suspend fun getNewsData(): NewsData = json.parse(httpGet(Endpoints.news))
}