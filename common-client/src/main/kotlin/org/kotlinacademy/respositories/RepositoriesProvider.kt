package org.kotlinacademy.respositories

expect object RepositoriesProvider {
    fun getNewsRepository(): NewsRepository
    fun getFeedbackRepository(): FeedbackRepository
    fun getInfoRepository(): InfoRepository
    fun getPuzzlersRepository(): PuzzlerRepository
    fun getNotificationRepository(): NotificationRepository
    fun getLogRepository(): LogRepository
}