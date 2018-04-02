package org.kotlinacademy.respositories

actual object RepositoriesProvider {
    actual fun getNewsRepository(): NewsRepository = NewsRepositoryImpl()
    actual fun getFeedbackRepository(): FeedbackRepository = FeedbackRepositoryImpl()
    actual fun getNotificationRepository(): NotificationRepository = NotificationRepositoryImpl()
    actual fun getInfoRepository(): InfoRepository = InfoRepositoryImpl()
    actual fun getPuzzlersRepository(): PuzzlerRepository = PuzzlerRepositoryImpl()

    actual fun getLogRepository(): LogRepository {
        TODO("not implemented") // We don't use this endpoint yet
    }

    actual fun getManagerRepository(): ManagerRepository {
        TODO("not implemented") // We don't use this endpoint yet
    }
}