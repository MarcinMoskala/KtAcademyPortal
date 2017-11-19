package com.marcinmoskala.kotlinacademy.backend.db

import com.marcinmoskala.kotlinacademy.data.News
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.Application
import io.ktor.application.log
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import kotlinx.coroutines.experimental.run
import org.jetbrains.squash.connection.DatabaseConnection
import org.jetbrains.squash.connection.Transaction
import org.jetbrains.squash.connection.transaction
import org.jetbrains.squash.dialects.h2.H2Connection
import org.jetbrains.squash.expressions.eq
import org.jetbrains.squash.query.select
import org.jetbrains.squash.query.where
import org.jetbrains.squash.results.get
import org.jetbrains.squash.statements.insertInto
import org.jetbrains.squash.statements.set
import org.jetbrains.squash.statements.update
import org.jetbrains.squash.statements.values
import kotlin.coroutines.experimental.CoroutineContext

class Database(application: Application) {
    private val dispatcher: CoroutineContext
    private val connectionPool: HikariDataSource
    private val connection: DatabaseConnection

    init {
        val config = application.environment.config.config("database")
        val url = config.property("connection").getString()
        val poolSize = config.property("poolSize").getString().toInt()
        application.log.info("Connecting to database at '$url'")

        dispatcher = newFixedThreadPoolContext(poolSize, "database-pool")
        val cfg = HikariConfig().apply {
            jdbcUrl = url
            maximumPoolSize = poolSize
            validate()
        }
        connectionPool = HikariDataSource(cfg)
        connection = H2Connection { connectionPool.connection }.apply {
            transaction { databaseSchema().create(listOf(NewsTable)) }
        }
    }

    suspend fun getNews(): List<News> = run(dispatcher) {
        connection.transaction {
            NewsTable.select(NewsTable.id, NewsTable.title, NewsTable.subtitle, NewsTable.imageUrl, NewsTable.url)
                    .execute()
                    .map {
                        News(
                                id = it[NewsTable.id],
                                title = it[NewsTable.title],
                                subtitle = it[NewsTable.subtitle],
                                imageUrl = it[NewsTable.imageUrl],
                                url = it[NewsTable.url]
                        )
                    }.toList()
        }
    }

    suspend fun updateOrAdd(news: News) {
        connection.transaction {
            val id = news.id
            if (id == null) {
                add(news)
            } else {
                when (countNewsWithId(id)) {
                    0 -> add(news)
                    1 -> update(id, news)
                    else -> throw Error("More then single element with id ${news.id} in the database")
                }
            }
        }
    }

    private fun Transaction.countNewsWithId(id: Int) = NewsTable.select(NewsTable.id)
            .where { NewsTable.id.eq(id) }
            .execute()
            .count()

    private fun Transaction.update(id: Int, news: News) {
        update(NewsTable)
                .where { NewsTable.id eq id }
                .set {
                    it[title] = news.title
                    it[subtitle] = news.subtitle
                    it[imageUrl] = news.imageUrl
                    it[url] = news.url
                }.execute()
    }

    private fun Transaction.add(news: News) {
        insertInto(NewsTable).values {
            it[title] = news.title
            it[subtitle] = news.subtitle
            it[imageUrl] = news.imageUrl
            it[url] = news.url
        }.execute()
    }
}