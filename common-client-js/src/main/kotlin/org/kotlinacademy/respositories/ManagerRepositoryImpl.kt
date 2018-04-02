package org.kotlinacademy.respositories

import org.kotlinacademy.Endpoints
import org.kotlinacademy.Endpoints.accept
import org.kotlinacademy.Endpoints.info
import org.kotlinacademy.Endpoints.news
import org.kotlinacademy.Endpoints.propose
import org.kotlinacademy.Endpoints.propositions
import org.kotlinacademy.Endpoints.puzzler
import org.kotlinacademy.Endpoints.reject
import org.kotlinacademy.data.*
import org.kotlinacademy.httpGet
import org.kotlinacademy.httpPost
import org.kotlinacademy.json

class ManagerRepositoryImpl : ManagerRepository {

    override suspend fun acceptInfo(id: Int, secret: String) {
        httpGet("$info/$id/$accept?Secret-hash=$secret")
    }

    override suspend fun acceptPuzzler(id: Int, secret: String) {
        httpGet("$puzzler/$id/$accept?Secret-hash=$secret")
    }

    override suspend fun rejectInfo(id: Int, secret: String) {
        httpGet("$info/$id/$reject?Secret-hash=$secret")
    }

    override suspend fun rejectPuzzler(id: Int, secret: String) {
        httpGet("$puzzler/$id/$reject?Secret-hash=$secret")
    }

    override suspend fun getPropositions(secret: String): NewsData {
        val str = httpGet("$news/$propositions?Secret-hash=$secret")
        val parsed = json.parse<NewsData>(str)
        val articles = parsed.articles.map { Article(it.id, ArticleData(it.data.title, it.data.subtitle, it.data.imageUrl, it.data.url, it.data.occurrence), it.dateTime) }
        val infos = parsed.infos.map { Info(it.id, InfoData(it.data.title, it.data.imageUrl, it.data.description, it.data.sources, it.data.url, it.data.author, it.data.authorUrl), it.dateTime, it.accepted) }
        val puzzlers = parsed.puzzlers.map { Puzzler(it.id, PuzzlerData(it.data.title, it.data.level, it.data.question, it.data.answers, it.data.correctAnswer, it.data.explanation, it.data.author, it.data.authorUrl), it.dateTime, it.accepted) }
        val newsData = NewsData(articles, infos, puzzlers)
        return newsData
    }
}