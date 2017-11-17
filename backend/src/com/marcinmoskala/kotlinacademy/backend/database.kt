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
import org.jetbrains.squash.query.select
import org.jetbrains.squash.results.get
import org.jetbrains.squash.statements.insertInto
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
            NewsTable.select(NewsTable.title, NewsTable.subtitle, NewsTable.imageUrl, NewsTable.url)
                    .execute()
                    .map {
                        News(
                                title = it.get<String>(0),
                                subtitle = it.get<String>(1),
                                imageUrl = it.get<String>(2),
                                url = it.get<String>(3)
                        )
                    }.toList()
        }
    }
}

object NewsTable : TableDefinition() {
    val id = integer("id").autoIncrement().primaryKey()
    val title = varchar("title", 50)
    val subtitle = varchar("subtitle", 50)
    val imageUrl = varchar("imageUrl", 50)
    val url = varchar("url", 50)
}