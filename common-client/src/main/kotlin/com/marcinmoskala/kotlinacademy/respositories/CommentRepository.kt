package com.marcinmoskala.kotlinacademy.respositories

import com.marcinmoskala.kotlinacademy.common.Provider
import com.marcinmoskala.kotlinacademy.data.Feedback

interface CommentRepository {

    suspend fun addComment(feedback: Feedback)

    companion object : Provider<CommentRepository>() {
        override fun create(): CommentRepository = RepositoriesProvider.getCommentRepository()
    }
}