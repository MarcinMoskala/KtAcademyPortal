package org.kotlinacademy.respositories

actual object RepositoriesProvider {
    actual fun getNewsRepository(): NewsRepository = NewsRepositoryImpl()
    actual fun getFeedbackRepository(): FeedbackRepository = FeedbackRepositoryImpl()
    actual fun getNotificationRepository(): NotificationRepository = NotificationsRepositoryImpl()
    actual fun getInfoRepository(): InfoRepository = InfoRepositoryImpl()
    actual fun getPuzzlersRepository(): PuzzlerRepository = PuzzlerRepositoryImpl()
    actual fun getLogRepository(): LogRepository = LogRepositoryImpl()
}