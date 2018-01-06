package org.kotlinacademy.respositories

import org.kotlinacademy.common.Provider
import org.kotlinacademy.data.Feedback

interface FeedbackRepository {

    suspend fun addFeedback(feedback: Feedback)

    companion object : Provider<FeedbackRepository>() {
        override fun create(): FeedbackRepository = RepositoriesProvider.getFeedbackRepository()
    }
}