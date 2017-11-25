package com.marcinmoskala.kotlinacademy.respositories

import com.marcinmoskala.kotlinacademy.Endpoints
import com.marcinmoskala.kotlinacademy.data.Feedback
import com.marcinmoskala.kotlinacademy.httpPost
import kotlinx.serialization.json.JSON

class CommentRepositoryImpl : CommentRepository {

    suspend override fun addComment(feedback: Feedback) {
        httpPost(JSON.stringify(feedback), Endpoints.feedback)
    }
}