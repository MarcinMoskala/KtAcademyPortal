package org.kotlinacademy.respositories

expect object RepositoriesProvider {
    fun getNewsRepository(): NewsRepository
    fun getFeedbackRepository(): FeedbackRepository
    fun getNotificationRepository(): NotificationRepository
}