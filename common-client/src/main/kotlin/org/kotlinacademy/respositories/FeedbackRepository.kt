package org.kotlinacademy.respositories

import org.kotlinacademy.data.Feedback

interface FeedbackRepository {

    suspend fun addFeedback(feedback: Feedback)
}