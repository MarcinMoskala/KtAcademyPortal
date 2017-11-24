package com.marcinmoskala.kotlinacademy.respositories

actual object RepositoriesProvider {

    private val newsRepository by lazy { NewsRepositoryImpl() }
    private val commentsRepository by lazy { CommentRepositoryImpl() }

    actual fun getNewsRepository(): NewsRepository = newsRepository
    actual fun getCommentRepository(): CommentRepository = commentsRepository
}