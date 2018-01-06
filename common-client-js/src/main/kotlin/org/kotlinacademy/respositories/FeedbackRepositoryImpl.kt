package org.kotlinacademy.respositories

import org.kotlinacademy.Endpoints
import org.kotlinacademy.data.Feedback
import org.kotlinacademy.httpPost
import org.kotlinacademy.json

class FeedbackRepositoryImpl : FeedbackRepository {

    suspend override fun addFeedback(feedback: Feedback) {
        httpPost(json.stringify(feedback), Endpoints.feedback)
    }
}