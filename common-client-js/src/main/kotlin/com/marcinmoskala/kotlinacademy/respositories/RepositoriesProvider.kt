package com.marcinmoskala.kotlinacademy.respositories

import com.marcinmoskala.kotlinacademy.DateTime
import com.marcinmoskala.kotlinacademy.DateTimeSerializer
import kotlinx.serialization.SerialContext
import kotlinx.serialization.json.JSON

actual object RepositoriesProvider {

    val jsonContext = SerialContext().apply { registerSerializer(DateTime::class, DateTimeSerializer) }
    val json = JSON(context = jsonContext)

    private val newsRepository by lazy { NewsRepositoryImpl(json) }
    private val commentsRepository by lazy { CommentRepositoryImpl(json) }

    actual fun getNewsRepository(): NewsRepository = newsRepository
    actual fun getCommentRepository(): CommentRepository = commentsRepository
}