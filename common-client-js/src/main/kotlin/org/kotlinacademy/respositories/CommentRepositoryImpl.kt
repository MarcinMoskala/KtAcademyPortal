package org.kotlinacademy.respositories

import org.kotlinacademy.Endpoints
import org.kotlinacademy.data.Feedback
import org.kotlinacademy.httpPost
import kotlinx.serialization.json.JSON

class CommentRepositoryImpl(val json: JSON) : CommentRepository {

    suspend override fun addComment(feedback: Feedback) {
        httpPost(json.stringify(feedback), Endpoints.feedback)
    }
}