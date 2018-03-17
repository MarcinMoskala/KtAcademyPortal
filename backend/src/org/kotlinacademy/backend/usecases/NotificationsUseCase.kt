package org.kotlinacademy.backend.usecases

import org.kotlinacademy.backend.repositories.db.TokenDatabaseRepository
import org.kotlinacademy.backend.repositories.email.EmailRepository
import org.kotlinacademy.backend.repositories.network.NotificationsRepository
import org.kotlinacademy.backend.repositories.network.dto.NotificationResult
import org.kotlinacademy.data.FirebaseTokenData
import org.kotlinacademy.data.FirebaseTokenType

object NotificationsUseCase {

    suspend fun send(body: String, url: String, tokenDatabaseRepository: TokenDatabaseRepository, notificationsRepository: NotificationsRepository, emailRepository: EmailRepository? = null) {
        val generalNotificationResponse = tokenDatabaseRepository
                .getAllTokens()
                .map { token -> NotificationsUseCase.send(body, url, token, notificationsRepository) }
                .reduce { acc, next -> NotificationResult(acc.success + next.success, acc.failure + next.failure) }

        if (emailRepository != null) {
            EmailUseCase.sendNotificationResult(generalNotificationResponse, emailRepository)
        }
    }

    suspend fun send(body: String, url: String, tokenData: FirebaseTokenData, notificationsRepository: NotificationsRepository): NotificationResult {
        val (token, type) = tokenData
        val title = "Kotlin Academy"
        val icon = when (type) {
            FirebaseTokenType.Android -> "icon_notification"
            FirebaseTokenType.Web -> "img/logo.png"
        }
        return notificationsRepository.sendNotification(title, body, icon, url, token)
    }
}