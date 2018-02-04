package org.kotlinacademy.respositories

import org.kotlinacademy.Endpoints
import org.kotlinacademy.data.News
import org.kotlinacademy.data.NewsData
import org.kotlinacademy.httpGet
import org.kotlinacademy.json
import org.kotlinacademy.parseDate

external val JSON: dynamic

class NewsRepositoryImpl : NewsRepository {
    override suspend fun getNewsData(): NewsData {
        val str = httpGet(Endpoints.news)
        // Error
        // val parsed = json.parse<NewsData>(str)
        // Workaround
        val parsed = JSON.parse(str)
        val news = parsed.news
        var newsList = listOf<News>()
        for (n in news) {
            newsList += News(
                    id = n.id,
                    title = n.title.toString(),
                    subtitle = n.subtitle.toString(),
                    imageUrl = n.imageUrl.toString(),
                    url = n.url?.toString(),
                    occurrence = n.occurrence.toString().parseDate()
            )
        }
        return NewsData(newsList)
    }
}