package org.kotlinacademy.backend.repositories.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.log
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import kotlinx.coroutines.experimental.run
import org.jetbrains.squash.connection.DatabaseConnection
import org.jetbrains.squash.connection.Transaction
import org.jetbrains.squash.connection.transaction
import org.jetbrains.squash.dialects.h2.H2Connection
import org.jetbrains.squash.dialects.postgres.PgConnection
import org.jetbrains.squash.expressions.eq
import org.jetbrains.squash.query.orderBy
import org.jetbrains.squash.query.select
import org.jetbrains.squash.query.where
import org.jetbrains.squash.results.get
import org.jetbrains.squash.statements.insertInto
import org.jetbrains.squash.statements.set
import org.jetbrains.squash.statements.update
import org.jetbrains.squash.statements.values
import org.kotlinacademy.backend.application
import org.kotlinacademy.data.Feedback
import org.kotlinacademy.data.FirebaseTokenData
import org.kotlinacademy.data.FirebaseTokenType
import org.kotlinacademy.data.FirebaseTokenType.Android
import org.kotlinacademy.data.FirebaseTokenType.Web
import org.kotlinacademy.data.News
import org.kotlinacademy.parseDate

class Database : DatabaseRepository {
    private val app = application ?: throw Error("DatabaseRepository must be overriten for unit tests")

    private val config = app.environment.config.config("database")
    private val poolSize = config.property("poolSize").getString().toInt()
    private val dispatcher = newFixedThreadPoolContext(poolSize, "database-pool")

    private val connection: DatabaseConnection

    init {
        val postgresUrl = System.getenv("JDBC_DATABASE_URL").takeUnless { it.isNullOrBlank() }
        connection = if (postgresUrl != null) {
            initPostgressDatabase(postgresUrl)
        } else {
            initH2Database()
        }
        connection.transaction {
            databaseSchema().create(listOf(NewsTable, FeedbackTable, TokensTable))
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

    override suspend fun getAllTokens(): List<FirebaseTokenData> = run(dispatcher) {
        connection.transaction {
            TokensTable.select(TokensTable.token, TokensTable.type)
                    .execute()
                    .map { FirebaseTokenData(it[TokensTable.token], it[TokensTable.type].toFirebaseTokenType()) }
                    .toList()
        }
    }

    override suspend fun addToken(tokenText: String, tokenType: FirebaseTokenType) {
        connection.transaction {
            insertInto(TokensTable).values {
                it[type] = tokenType.toValueName()
                it[token] = tokenText
            }.execute()
        }
    }

    private fun initPostgressDatabase(postgresUrl: String): DatabaseConnection {
        val config = HikariConfig().apply {
            jdbcUrl = postgresUrl
            maximumPoolSize = poolSize
            validate()
        }
        return PgConnection { HikariDataSource(config).connection }
    }

    private fun initH2Database(): DatabaseConnection {
        val url = config.property("connection").getString()
        val config = HikariConfig().apply {
            jdbcUrl = url
            maximumPoolSize = poolSize
            validate()
        }
        return H2Connection { HikariDataSource(config).connection }
    }

    private fun FirebaseTokenType.toValueName(): String = when (this) {
        Web -> "web"
        Android -> "android"
    }

    private fun String.toFirebaseTokenType(): FirebaseTokenType = when (this) {
        "web" -> Web
        "android" -> Android
        else -> throw Error("Illegal type $this set as furebase token type")
    }

    private fun Transaction.countNewsWithId(id: Int) = NewsTable.select(NewsTable.id)
            .where { NewsTable.id.eq(id) }
            .execute()
            .count()
}