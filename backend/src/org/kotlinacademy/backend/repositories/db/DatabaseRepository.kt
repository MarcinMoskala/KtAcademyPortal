package org.kotlinacademy.backend.repositories.db

import org.kotlinacademy.common.Provider
import org.kotlinacademy.data.Feedback
import org.kotlinacademy.data.FirebaseTokenData
import org.kotlinacademy.data.FirebaseTokenType
import org.kotlinacademy.data.News

interface DatabaseRepository {

    suspend fun getNews(): List<News>
    suspend fun getNews(id: Int): News
    suspend fun addNews(news: News)
    suspend fun deleteNews(newsId: Int)
    suspend fun updateNews(id: Int, news: News)
    suspend fun getFeedback(): List<Feedback>
    suspend fun addFeedback(feedback: Feedback)
    suspend fun getAllTokens(): List<FirebaseTokenData>
    suspend fun addToken(token: String, tokenType: FirebaseTokenType)

    companion object: Provider<DatabaseRepository>() {
        override fun create(): DatabaseRepository = Database
    }
}