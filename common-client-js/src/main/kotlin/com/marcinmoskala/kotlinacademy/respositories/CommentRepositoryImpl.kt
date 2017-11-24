package com.marcinmoskala.kotlinacademy.respositories

import com.marcinmoskala.kotlinacademy.Endpoints
import com.marcinmoskala.kotlinacademy.data.Comment
import com.marcinmoskala.kotlinacademy.httpGet
import com.marcinmoskala.kotlinacademy.httpPost
import kotlinx.serialization.json.JSON

class CommentRepositoryImpl : CommentRepository {

    suspend override fun addComment(comment: Comment) {
        httpPost(JSON.stringify(comment), Endpoints.comments)
    }
}