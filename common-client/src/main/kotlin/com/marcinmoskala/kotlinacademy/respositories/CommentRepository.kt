package com.marcinmoskala.kotlinacademy.respositories

import com.marcinmoskala.kotlinacademy.common.Provider
import com.marcinmoskala.kotlinacademy.data.Comment
import com.marcinmoskala.kotlinacademy.data.NewsData

interface CommentRepository {

    suspend fun addComment(comment: Comment)

    companion object : Provider<CommentRepository>() {
        override fun create(): CommentRepository = RepositoriesProvider.getCommentRepository()
    }
}