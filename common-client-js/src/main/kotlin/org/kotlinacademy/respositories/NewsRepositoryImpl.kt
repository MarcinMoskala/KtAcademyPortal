package org.kotlinacademy.respositories

import org.kotlinacademy.Endpoints
import org.kotlinacademy.data.*
import org.kotlinacademy.httpGet
import org.kotlinacademy.json

class NewsRepositoryImpl : NewsRepository {

    // HACK: Serialization objects does not correspond correctly to real objects so we need to repack them all
    override suspend fun getNewsData(): NewsData {
        val str = httpGet(Endpoints.news)
        val parsed = json.parse<NewsData>(str)
        val articles = parsed.articles.map { Article(it.id, ArticleData(it.data.title, it.data.subtitle, it.data.imageUrl, it.data.url, it.data.occurrence), it.dateTime) }
        val infos = parsed.infos.map { Info(it.id, InfoData(it.data.title, it.data.imageUrl, it.data.description, it.data.sources, it.data.url, it.data.author, it.data.authorUrl), it.dateTime, it.accepted) }
        val puzzlers = parsed.puzzlers.map { Puzzler(it.id, PuzzlerData(it.data.title, it.data.question, it.data.answers, it.data.author, it.data.authorUrl), it.dateTime, it.accepted) }
        val newsData = NewsData(articles, infos, puzzlers)
        return newsData
    }
}