package org.kotlinacademy.backend.usecases

import com.rometools.rome.feed.synd.SyndContentImpl
import com.rometools.rome.feed.synd.SyndEntry
import com.rometools.rome.feed.synd.SyndEntryImpl
import com.rometools.rome.feed.synd.SyndFeedImpl
import com.rometools.rome.io.SyndFeedOutput
import org.kotlinacademy.backend.Config.baseUrl
import org.kotlinacademy.backend.repositories.db.ArticlesDatabaseRepository
import org.kotlinacademy.backend.repositories.db.InfoDatabaseRepository
import org.kotlinacademy.backend.repositories.db.InfoTable.description
import org.kotlinacademy.backend.repositories.db.PuzzlersDatabaseRepository
import org.kotlinacademy.data.*
import java.io.StringWriter


object RssUseCase {

    suspend fun getRssFeed(): String {
        val articlesDatabaseRepository = ArticlesDatabaseRepository.get()
        val infoDatabaseRepository = InfoDatabaseRepository.get()
        val puzzlersDatabaseRepository = PuzzlersDatabaseRepository.get()

        val articles = articlesDatabaseRepository.getArticles()
        val infos = infoDatabaseRepository.getInfos()
        val puzzlers = puzzlersDatabaseRepository.getPuzzlers()

        val news = (articles + infos + puzzlers).sortedByDescending { it.dateTime }

        val feed = SyndFeedImpl().apply {
            feedType = "rss_2.0"
            title = "Kotlin Academy news"
            link = "kotlin-academy.com"
            description = "All great newses from Kotlin Academy portal"
        }

        feed.entries = news.map(RssUseCase::toRssEntry)
        val writer = StringWriter()
        val output = SyndFeedOutput()
        output.output(feed, writer)
        return writer.toString()
    }

    private fun toRssEntry(news: News): SyndEntry? = when (news) {
        is Article -> toRssEntry(news)
        is Info -> toRssEntry(news)
        is Puzzler -> toRssEntry(news)
        else -> null
    }

    private fun toRssEntry(article: Article) = SyndEntryImpl().apply {
        title = article.title
        link = article.url
        publishedDate = article.occurrence.toDate()
        description = SyndContentImpl().apply {
            type = "text/plain"
            value = article.subtitle
        }
    }

    private fun toRssEntry(info: Info) = SyndEntryImpl().apply {
        title = info.title
        link = baseUrl
        publishedDate = info.dateTime.toDate()
        description = SyndContentImpl().apply {
            type = "text/plain"
            value = info.description
        }
    }

    private fun toRssEntry(puzzler: Puzzler) = SyndEntryImpl().apply {
        title = puzzler.title
        link = baseUrl
        publishedDate = puzzler.dateTime.toDate()
        description = SyndContentImpl().apply {
            type = "text/plain"
            value = puzzler.question
        }
    }
}