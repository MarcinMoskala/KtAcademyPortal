package org.kotlinacademy.respositories

actual object RepositoriesProvider {

    actual fun getNewsRepository(): NewsRepository = NewsRepositoryImpl()
    actual fun getCommentRepository(): CommentRepository = CommentRepositoryImpl()
    actual fun getNotificationRepository(): NotificationRepository = NotificationsRepositoryImpl()
}