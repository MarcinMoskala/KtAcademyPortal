package org.kotlinacademy.backend.usecases

import com.rometools.rome.feed.synd.SyndContentImpl
import com.rometools.rome.feed.synd.SyndEntry
import com.rometools.rome.feed.synd.SyndEntryImpl
import com.rometools.rome.feed.synd.SyndFeedImpl
import com.rometools.rome.io.SyndFeedOutput
import org.jdom2.Element
import org.jdom2.Namespace
import org.kotlinacademy.backend.Config.baseUrl
import org.kotlinacademy.backend.common.toDate
import org.kotlinacademy.backend.repositories.db.ArticlesDatabaseRepository
import org.kotlinacademy.backend.repositories.db.InfoDatabaseRepository
import org.kotlinacademy.backend.repositories.db.PuzzlersDatabaseRepository
import org.kotlinacademy.data.*
import java.io.StringWriter


object RssUseCase {

    suspend fun getRssFeed(): String = getFeed("rss_2.0")
    suspend fun getAtomFeed(): String = getFeed("atom_0.3")

    private suspend fun getFeed(type: String): String {
        val articlesDatabaseRepository = ArticlesDatabaseRepository.get()
        val infoDatabaseRepository = InfoDatabaseRepository.get()
        val puzzlersDatabaseRepository = PuzzlersDatabaseRepository.get()

        val articles = articlesDatabaseRepository.getArticles()
        val infos = infoDatabaseRepository.getInfos()
        val puzzlers = puzzlersDatabaseRepository.getPuzzlers()

        val news = (articles + infos + puzzlers).sortedByDescending { it.dateTime }

        val feed = SyndFeedImpl().apply {
            feedType = type
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
        setImageUrl(article.imageUrl)
        description = SyndContentImpl().apply {
            type = "text/plain"
            value = article.subtitle
        }
    }

    private fun toRssEntry(info: Info) = SyndEntryImpl().apply {
        title = info.title
        link = baseUrl
        publishedDate = info.dateTime.toDate()
        setImageUrl(info.imageUrl)
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
            value = puzzler.codeQuestion
        }
    }

    private fun SyndEntry.setImageUrl(image: String) {
        val imageTag = Element("image", Namespace.getNamespace("image", "http://web.resource.org/rss/1.0/modules/image/")).apply {
            addContent(image)
        }
        foreignMarkup.add(imageTag)
    }
}