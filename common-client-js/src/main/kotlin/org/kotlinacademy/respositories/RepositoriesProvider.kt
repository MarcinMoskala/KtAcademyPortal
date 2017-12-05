package org.kotlinacademy.respositories

import org.kotlinacademy.DateTime
import org.kotlinacademy.DateTimeSerializer
import kotlinx.serialization.SerialContext
import kotlinx.serialization.json.JSON

actual object RepositoriesProvider {

    private val jsonContext = SerialContext().apply { registerSerializer(DateTime::class, DateTimeSerializer) }
    private val json = JSON(context = jsonContext)

    actual fun getNewsRepository(): NewsRepository = NewsRepositoryImpl(json)
    actual fun getCommentRepository(): CommentRepository = CommentRepositoryImpl(json)
    actual fun getNotificationRepository(): NotificationRepository = NotificationsRepositoryImpl(json)
}