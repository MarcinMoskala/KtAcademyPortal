package org.kotlinacademy.backend.usecases

import org.kotlinacademy.backend.repositories.db.TokenDatabaseRepository
import org.kotlinacademy.backend.repositories.network.notifications.NotificationsRepository
import org.kotlinacademy.backend.repositories.network.notifications.NotificationResult
import org.kotlinacademy.data.FirebaseTokenData
import org.kotlinacademy.data.FirebaseTokenType

object NotificationsUseCase {

    suspend fun send(body: String, url: String) {
        val tokenDatabaseRepository = TokenDatabaseRepository.get()
        val generalNotificationResponse = tokenDatabaseRepository
                .getAllTokens()
                .map { token -> NotificationsUseCase.send(body, url, token) }
                .fold(NotificationResult(0, 0)) { acc, next -> NotificationResult(acc.success + next.success, acc.failure + next.failure) }

        EmailUseCase.sendNotificationResult(generalNotificationResponse)
    }

    suspend fun send(body: String, url: String, tokenData: FirebaseTokenData): NotificationResult {
        val notificationsRepository = NotificationsRepository.get()
                ?: return NotificationResult(0, 0)
        val (token, type) = tokenData
        val title = "Kotlin Academy"
        val icon = when (type) {
            FirebaseTokenType.Android -> "icon_notification"
            FirebaseTokenType.Web -> "img/logo.png"
        }
        return notificationsRepository.sendNotification(title, body, icon, url, token)
    }
}