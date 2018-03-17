package org.kotlinacademy.backend.repositories.db

import org.kotlinacademy.common.Provider
import org.kotlinacademy.data.*

interface ArticlesDatabaseRepository {

    suspend fun getArticles(): List<Article>
    suspend fun getAcceptedArticles(): List<Article>
    suspend fun getArticle(id: Int): Article
    suspend fun addArticle(article: Article, isAccepted: Boolean)
    suspend fun deleteArticle(id: Int)
    suspend fun updateArticle(id: Int, article: Article, isAccepted: Boolean? = null)

    companion object: Provider<ArticlesDatabaseRepository>() {
        override fun create(): ArticlesDatabaseRepository = Database.articlesDatabase
    }
}