package org.kotlinacademy.respositories

expect object RepositoriesProvider {
    fun getNewsRepository(): NewsRepository
    fun getCommentRepository(): CommentRepository
    fun getNotificationRepository(): NotificationRepository
}