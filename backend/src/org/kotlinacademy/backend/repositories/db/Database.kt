package org.kotlinacademy.backend.repositories.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import kotlinx.coroutines.experimental.run
import org.jetbrains.squash.connection.DatabaseConnection
import org.jetbrains.squash.connection.Transaction
import org.jetbrains.squash.connection.transaction
import org.jetbrains.squash.dialects.h2.H2Connection
import org.jetbrains.squash.dialects.postgres.PgConnection
import org.jetbrains.squash.query.select
import org.jetbrains.squash.results.get
import org.jetbrains.squash.statements.deleteFrom
import org.jetbrains.squash.statements.update
import org.kotlinacademy.backend.application
import org.kotlinacademy.backend.logInfo
import org.kotlinacademy.data.ArticleData
import org.kotlinacademy.now
import org.kotlinacademy.parseDateTime

object Database {

    val articlesDatabase = ArticlesDatabase()
    val infoDatabase = InfoDatabase()
    val puzzlersDatabase = PuzzlersDatabase()
    val tokenDatabase = TokenDatabase()
    val feedbackDatabase = FeedbackDatabase()
    val logDatabase = LogDatabase()

    private val app = application
            ?: throw Error("FeedbackDatabaseRepository must be overridden for unit tests")

    private val config = app.environment.config.config("database")
    private val poolSize = config.property("poolSize").getString().toInt()

    private val connection: DatabaseConnection by lazy {
        val postgresUrl = System.getenv("JDBC_DATABASE_URL").takeUnless { it.isNullOrBlank() }
        if (postgresUrl != null) initPostgressDatabase(postgresUrl) else initH2Database()
    }

    private val dispatcher = newFixedThreadPoolContext(poolSize, "database-pool")

    init {
        connection.transaction {
            databaseSchema().create(listOf(NewsTable, FeedbackTable, TokensTable, ArticlesTable, InfoTable, PuzzlersTable, LogTable))
        }
        addActualQuestionToPuzzlers()
        migrateNewsToArticles()
    }

    suspend fun <T> makeTransaction(f: Transaction.() -> T): T = run(dispatcher) {
        connection.transaction {
            f()
        }
    }

    private fun addActualQuestionToPuzzlers() = launch(dispatcher) {
        try {
            puzzlersDatabase.getPuzzlers()
        } catch (e: org.postgresql.util.PSQLException) {
            connection.transaction {
                executeStatement("ALTER TABLE puzzlers ADD actualquestion text;")
                update(PuzzlersTable)
                        .set(PuzzlersTable.actualQuestion, "What does it display? Some possibilities:")
                        .execute()
            }
        }
    }

    private fun migrateNewsToArticles() = launch(dispatcher) {
        val newsAsArticlesDatas = connection.transaction {
            NewsTable.select(NewsTable.id, NewsTable.title, NewsTable.subtitle, NewsTable.imageUrl, NewsTable.url, NewsTable.occurrence)
                    .execute()
                    .map {
                        ArticleData(
                                title = it[NewsTable.title],
                                subtitle = it[NewsTable.subtitle],
                                imageUrl = it[NewsTable.imageUrl],
                                url = it[NewsTable.url],
                                occurrence = it[NewsTable.occurrence].takeUnless { it.isNullOrBlank() }?.parseDateTime()
                                        ?: now
                        )
                    }.toList()
        }
        for (articleData in newsAsArticlesDatas) {
            articlesDatabase.addArticle(articleData)
        }
        connection.transaction {
            deleteFrom(NewsTable).execute()
        }
    }

    private fun initPostgressDatabase(postgresUrl: String): DatabaseConnection {
        logInfo("I connect to Postgress database $postgresUrl")
        val hikariConfig = HikariConfig().apply {
            jdbcUrl = postgresUrl
            maximumPoolSize = poolSize
            validate()
        }
        val dataSource = HikariDataSource(hikariConfig)
        return PgConnection { dataSource.connection }
    }

    private fun initH2Database(): DatabaseConnection {
        logInfo("I connect to H2 database")
        val url = config.property("connection").getString()
        val config = HikariConfig().apply {
            jdbcUrl = url
            maximumPoolSize = poolSize
            validate()
        }
        val dataSource = HikariDataSource(config)
        return H2Connection { dataSource.connection }
    }
}