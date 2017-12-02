package com.marcinmoskala.kotlinacademy.backend.db

import com.marcinmoskala.kotlinacademy.common.Provider
import com.marcinmoskala.kotlinacademy.data.Feedback
import com.marcinmoskala.kotlinacademy.data.News

interface DatabaseRepository {

    suspend fun getNews(): List<News>
    suspend fun addOrReplaceNews(news: News)
    suspend fun getFeedback(): List<Feedback>
    suspend fun addFeedback(feedback: Feedback)
    suspend fun getWebTokens(): List<String>
    suspend fun addWebToken(token: String)

    companion object: Provider<DatabaseRepository>() {
        override fun create(): DatabaseRepository = Database()
    }
}