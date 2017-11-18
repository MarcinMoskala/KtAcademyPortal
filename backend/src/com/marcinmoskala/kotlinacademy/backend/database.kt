package com.marcinmoskala.kotlinacademy.backend

import com.marcinmoskala.kotlinacademy.data.News
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.Application
import io.ktor.application.log
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import kotlinx.coroutines.experimental.run
import org.jetbrains.squash.connection.DatabaseConnection
import org.jetbrains.squash.connection.transaction
import org.jetbrains.squash.definition.*
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
        val cfg = HikariConfig()
        cfg.jdbcUrl = url
        cfg.maximumPoolSize = poolSize
        cfg.validate()

        connectionPool = HikariDataSource(cfg)

        connection = H2Connection { connectionPool.connection }.apply {
            transaction { databaseSchema().create(listOf(NewsTable)) }
        }
    }

    suspend fun addNews(news: News): Boolean = run(dispatcher) {
        connection.transaction {
            insertInto(NewsTable)
                    .values {
                        it[title] = news.title
                        it[subtitle] = news.subtitle
                        it[imageUrl] = news.imageUrl
                        it[url] = news.url
                    }
                    .execute()
            true
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
            val itemsWithId = NewsTable.select(NewsTable.id)
                    .where { NewsTable.id.eq(news.id) }
                    .execute()
                    .count()

            val doUpdate = when (itemsWithId) {
                0 -> false
                1 -> true
                else -> throw Error("More then single element with id ${news.id} in the database")
            }

            if (doUpdate) {
                update(NewsTable)
                        .where { NewsTable.id eq news.id }
                        .set {
                            it[NewsTable.title] = news.title
                            it[NewsTable.subtitle] = news.subtitle
                            it[NewsTable.imageUrl] = news.imageUrl
                            it[NewsTable.url] = news.url
                        }.execute()
            } else {
                insertInto(NewsTable).values {
                    it[NewsTable.title] = news.title
                    it[NewsTable.subtitle] = news.subtitle
                    it[NewsTable.imageUrl] = news.imageUrl
                    it[NewsTable.url] = news.url
                }.execute()
            }
        }
    }
}

object NewsTable : TableDefinition() {
    val id = integer("id").autoIncrement().primaryKey()
    val title = varchar("title", 50)
    val subtitle = varchar("subtitle", 250)
    val imageUrl = varchar("imageUrl", 250)
    val url = varchar("url", 50)
}