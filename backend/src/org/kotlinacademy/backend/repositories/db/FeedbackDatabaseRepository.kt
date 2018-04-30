package org.kotlinacademy.backend.repositories.db

import org.kotlinacademy.backend.common.Provider
import org.kotlinacademy.data.Feedback

interface FeedbackDatabaseRepository {

    suspend fun getFeedback(): List<Feedback>
    suspend fun addFeedback(feedback: Feedback)

    companion object: Provider<FeedbackDatabaseRepository>() {
        override fun create(): FeedbackDatabaseRepository = Database.feedbackDatabase
    }
}