package org.kotlinacademy.respositories

import org.kotlinacademy.Endpoints
import org.kotlinacademy.data.NewsData
import org.kotlinacademy.httpGet
import org.kotlinacademy.json

class NewsRepositoryImpl : NewsRepository {

    override suspend fun getNewsData(): NewsData =
            json.parse(httpGet(Endpoints.news))
}