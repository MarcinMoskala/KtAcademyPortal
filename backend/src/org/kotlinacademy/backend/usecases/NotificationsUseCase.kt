package org.kotlinacademy.backend.usecases

import org.kotlinacademy.backend.repositories.db.TokenDatabaseRepository
import org.kotlinacademy.backend.repositories.network.notifications.NotificationData
import org.kotlinacademy.backend.repositories.network.notifications.NotificationResult
import org.kotlinacademy.backend.repositories.network.notifications.NotificationsRepository
import org.kotlinacademy.data.FirebaseTokenType

object NotificationsUseCase {

    suspend fun sendToAll(body: String, url: String) {
        val tokenDatabaseRepository = TokenDatabaseRepository.get()
        val generalNotificationResponse = tokenDatabaseRepository
                .getAllTokens()
                .map { (token, type) -> NotificationsUseCase.send(body, url, token, type) }
                .fold(NotificationResult(0, 0)) { acc, next -> NotificationResult(acc.success + next.success, acc.failure + next.failure) }

        EmailUseCase.sendNotificationResult(generalNotificationResponse)
    }

    suspend fun send(body: String, url: String, token: String, type: FirebaseTokenType): NotificationResult {
        val notificationsRepository = NotificationsRepository.get()
                ?: return NotificationResult(0, 0)
        val title = "Kotlin Academy"
        val data = when (type) {
            FirebaseTokenType.Android -> NotificationData(title, body, "icon_notification")
            FirebaseTokenType.Web -> NotificationData(title, body, "img/logo.png", url)
        }
        return notificationsRepository.sendNotification(token, data)
    }
}