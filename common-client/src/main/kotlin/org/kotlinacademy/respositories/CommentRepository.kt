package org.kotlinacademy.respositories

import org.kotlinacademy.common.Provider
import org.kotlinacademy.data.Feedback

interface CommentRepository {

    suspend fun addComment(feedback: Feedback)

    companion object : Provider<CommentRepository>() {
        override fun create(): CommentRepository = RepositoriesProvider.getCommentRepository()
    }
}