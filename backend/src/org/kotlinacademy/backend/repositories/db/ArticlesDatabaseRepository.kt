package org.kotlinacademy.backend.repositories.db

import org.kotlinacademy.backend.common.Provider
import org.kotlinacademy.data.Article
import org.kotlinacademy.data.ArticleData

interface ArticlesDatabaseRepository {

    suspend fun getArticles(): List<Article>
    suspend fun getArticle(id: Int): Article
    suspend fun addArticle(article: ArticleData)
    suspend fun deleteArticle(id: Int)
    suspend fun updateArticle(article: Article)

    companion object: Provider<ArticlesDatabaseRepository>() {
        override fun create(): ArticlesDatabaseRepository = Database.articlesDatabase
    }
}