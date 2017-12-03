package com.marcinmoskala.kotlinacademy.backend.repositories.db

import com.marcinmoskala.kotlinacademy.backend.application
import com.marcinmoskala.kotlinacademy.backend.usecases.TokenType
import com.marcinmoskala.kotlinacademy.data.Feedback
import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.parseDate
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.log
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import kotlinx.coroutines.experimental.run
import org.jetbrains.squash.connection.DatabaseConnection
import org.jetbrains.squash.connection.Transaction
import org.jetbrains.squash.connection.transaction
import org.jetbrains.squash.dialects.h2.H2Connection
import org.jetbrains.squash.expressions.eq
import org.jetbrains.squash.query.orderBy
import org.jetbrains.squash.query.select
import org.jetbrains.squash.query.where
import org.jetbrains.squash.results.get
import org.jetbrains.squash.statements.insertInto
import org.jetbrains.squash.statements.set
import org.jetbrains.squash.statements.update
import org.jetbrains.squash.statements.values
import kotlin.coroutines.experimental.CoroutineContext

class Database : DatabaseRepository {
    private val app = application ?: throw Error("DatabaseRepository must be overriten for unit tests")

    private val dispatcher: CoroutineContext
    private val connectionPool: HikariDataSource
    private val connection: DatabaseConnection

    init {
        val config = app.environment.config.config("database")
        val url = config.property("connection").getString()
        val poolSize = config.property("poolSize").getString().toInt()
        app.log.info("Connecting to database at '$url'")

        dispatcher = newFixedThreadPoolContext(poolSize, "database-pool")
        val cfg = HikariConfig().apply {
            jdbcUrl = url
            maximumPoolSize = poolSize
            validate()
        }
        connectionPool = HikariDataSource(cfg)
        connection = H2Connection { connectionPool.connection }.apply {
            transaction { databaseSchema().create(listOf(NewsTable, FeedbackTable, TokensTable)) }
        }
    }

    override suspend fun getNews(): List<News> = run(dispatcher) {
        connection.transaction {
            NewsTable.select(NewsTable.id, NewsTable.title, NewsTable.subtitle, NewsTable.imageUrl, NewsTable.url, NewsTable.occurrence)
                    .orderBy(ascending = false) { NewsTable.id }
                    .execute()
                    .map {
                        News(
                                id = it[NewsTable.id],
                                title = it[NewsTable.title],
                                subtitle = it[NewsTable.subtitle],
                                imageUrl = it[NewsTable.imageUrl],
                                url = it[NewsTable.url],
                                occurrence = it[NewsTable.occurrence].parseDate()
                        )
                    }.toList()
                    .reversed()
        }
    }

    override suspend fun addNews(news: News) {
        connection.transaction {
            insertInto(NewsTable).values {
                it[title] = news.title
                it[subtitle] = news.subtitle
                it[imageUrl] = news.imageUrl
                it[url] = news.url
                it[occurrence] = news.occurrence.toDateFormatString()
            }.execute()
        }
    }

    override suspend fun updateNews(id: Int, news: News) {
        connection.transaction {
            val countSuchNews = countNewsWithId(id)
            if (countSuchNews != 1) throw Error("News Id not found")
            update(NewsTable)
                    .where { NewsTable.id eq id }
                    .set {
                        it[title] = news.title
                        it[subtitle] = news.subtitle
                        it[imageUrl] = news.imageUrl
                        it[url] = news.url
                        it[occurrence] = news.occurrence.toDateFormatString()
                    }.execute()
        }
    }

    override suspend fun getFeedback(): List<Feedback> = run(dispatcher) {
        connection.transaction {
            FeedbackTable.select(FeedbackTable.newsId, FeedbackTable.rating, FeedbackTable.commentText, FeedbackTable.suggestionsText)
                    .execute()
                    .distinct()
                    .map { Feedback(it[FeedbackTable.newsId], it[FeedbackTable.rating], it[FeedbackTable.commentText], it[FeedbackTable.suggestionsText]) }
                    .toList()
        }
    }

    override suspend fun addFeedback(feedback: Feedback) {
        connection.transaction {
            insertInto(FeedbackTable).values {
                it[newsId] = feedback.newsId
                it[rating] = feedback.rating
                it[commentText] = feedback.comment
                it[suggestionsText] = feedback.suggestions
            }.execute()
        }
    }

    override suspend fun getTokens(tokenType: TokenType): List<String> = run(dispatcher) {
        connection.transaction {
            TokensTable.select(TokensTable.token)
                    .where { TokensTable.type.eq(tokenType.toValueName()) }
                    .execute()
                    .map { it[TokensTable.token] }
                    .toList()
        }
    }

    override suspend fun addToken(tokenText: String, tokenType: TokenType) {
        connection.transaction {
            insertInto(TokensTable).values {
                it[type] = tokenType.toValueName()
                it[token] = tokenText
            }.execute()
        }
    }

    private fun TokenType.toValueName(): String = when (this) {
        TokenType.Web -> "web"
        TokenType.Android -> "android"
    }

    private fun Transaction.countNewsWithId(id: Int) = NewsTable.select(NewsTable.id)
            .where { NewsTable.id.eq(id) }
            .execute()
            .count()
}