package com.marcinmoskala.kotlinacademy.backend.repositories.db

import com.marcinmoskala.kotlinacademy.common.Provider
import com.marcinmoskala.kotlinacademy.data.Feedback
import com.marcinmoskala.kotlinacademy.data.FirebaseTokenData
import com.marcinmoskala.kotlinacademy.data.FirebaseTokenType
import com.marcinmoskala.kotlinacademy.data.News

interface DatabaseRepository {

    suspend fun getNews(): List<News>
    suspend fun addNews(news: News)
    suspend fun updateNews(id: Int, news: News)
    suspend fun getFeedback(): List<Feedback>
    suspend fun addFeedback(feedback: Feedback)
    suspend fun getAllTokens(): List<FirebaseTokenData>
    suspend fun addToken(token: String, tokenType: FirebaseTokenType)

    companion object: Provider<DatabaseRepository>() {
        override fun create(): DatabaseRepository = Database()
    }
}